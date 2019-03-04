package com.tpdds.usuarios;

import com.tpdds.converters.BoolToBit;
import com.tpdds.categorias.Categoria;
import com.tpdds.categorias.DeterminadorDeCategoria;
import com.tpdds.converters.LocalToDate;
import com.tpdds.dispositivos.*;
import com.tpdds.transformadores.Posicion;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private long idCliente;

    @OneToMany(targetEntity = DispositivoEstandar.class)
    @JoinColumn(name = "cliente")
    private List<DispositivoEstandar> dispositivosEstandar;

    @OneToMany(targetEntity = DispositivoInteligente.class)
    @JoinColumn(name = "cliente")
    private List<DispositivoInteligente> dispositivosInteligentes;

    private String nombre;
    private String apellido;
    private String tipoDoc;
    private String documento;
    private String telefono;
    private String domicilio;
    @Convert(converter = LocalToDate.class)
    private LocalDate fechaDeAlta;
    @OneToOne(cascade = {CascadeType.ALL})
    private Categoria categoria;
    @Embedded
    private Posicion posicion;
    @Convert(converter = BoolToBit.class)
    private Boolean ahorroAutomatico; // Para indicar si el usuario quiere que se accione por si solo la sugerencia del simplex

    private String user;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String tipoDoc, String documento, String telefono, String domicilio,
                   LocalDate fechaDeAlta, Posicion posicion, Boolean ahorroAutomatico, String user) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.fechaDeAlta = fechaDeAlta;
        this.dispositivosEstandar = new ArrayList<>();
        this.dispositivosInteligentes = new ArrayList<>();
        this.posicion = posicion;
        this.categoria = calcularCategoria();
        this.ahorroAutomatico = ahorroAutomatico;
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setDispositivosEstandar(List<DispositivoEstandar> dispositivosEstandars) {
        this.dispositivosEstandar = dispositivosEstandars;
    }

    public void setDispositivosInteligentes(List<DispositivoInteligente> dispositivosInteligentes) {
        this.dispositivosInteligentes = dispositivosInteligentes;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public ArrayList<Dispositivo> getAllDispositivos() {
        ArrayList<Dispositivo> list = new ArrayList<>();
        list.addAll(dispositivosEstandar);
        list.addAll(dispositivosInteligentes);

        return list;
    }

    public String getUser() {
        return this.user;
    }

    public boolean algunDispositivoEncendido() {
        return dispositivosInteligentes.stream().anyMatch(DispositivoInteligente::getEstado);
    }

    public long cantidadDispositivosEncendidos() {
        return dispositivosInteligentes.stream().filter(DispositivoInteligente::getEstado).count();
    }

    public long cantidadDispositivosApagados() {
        return dispositivosInteligentes.stream().filter(d -> !d.getEstado()).count();
    }

    public int cantidadTotalDeDispositivos() {
        return dispositivosEstandar.size();
    }

    private Categoria calcularCategoria() {
        return DeterminadorDeCategoria.getInstance().calcularCategoria(this.calcularConsumoDeDispositivos());
    }

    public BigDecimal calcularConsumoDeDispositivos() {
        return this.getAllDispositivos()
                .stream()
                .map(Dispositivo::getConsumoMensual)
                .reduce(new BigDecimal(0), BigDecimal::add);

    }

    public BigDecimal calcularConsumoEnIntervalo(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.getAllDispositivos().stream().map(disp -> disp.pedirConsumo(fechaInicio, fechaFin)).reduce(new BigDecimal(0),
                BigDecimal::add);
    }

    public void adaptar(DispositivoEstandar dispositivoEstandar, Fabricante f) {
        this.dispositivosInteligentes.add(dispositivoEstandar.hacerseInteligente(f));
        dispositivosEstandar.remove(dispositivoEstandar);
    }

    public String getID() {
        return documento;
    }

    public void agregarDispositivo(DispositivoEstandar d) {
        this.dispositivosEstandar.add(d);
    }

    public void recategorizar() {
        this.categoria = this.calcularCategoria();
    }

    public boolean isAhorroAutomatico() {
        return ahorroAutomatico;
    }

    public void setAhorroAutomatico(boolean ahorroAutomatico) {
        this.ahorroAutomatico = ahorroAutomatico;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(BigDecimal x, BigDecimal y) {
        this.posicion = new Posicion(x, y);
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void agregarDispositivoInteligente(DispositivoInteligente inteligente) {
        dispositivosInteligentes.add(inteligente);
    }

    public long getConsumo() {
        return calcularConsumoDeDispositivos().longValue();
    }

    public List<DispositivoEstandar> getDispositivosEstandar() {
        return dispositivosEstandar;
    }

    public List<DispositivoInteligente> getDispositivosInteligentes() {
        return dispositivosInteligentes;
    }

    public Map<YearMonth, Double> getConsumosPorPeriodo() {
        final Map<YearMonth, List<Periodo>> collect =
                this.getAllDispositivos()
                        .stream() // Stream<Dispositivo>
                        .map(Dispositivo::getPeriodos) // Stream<List<Periodo>> tengo listas de periodos, separados por disp.
                        .flatMap(Collection::stream) // Stream<Periodo> las junto en una sola lista
                        .collect(Collectors.groupingBy(Periodo::getFecha));// Map<YearMonth, Periodo> las separo por periodo

        /* tengo un mapa que usa de clave YearMonth, y de valor tiene una lista de periodos
         * ahora quiero reducir esta lista de periodos a un valor de consumo
         * ademas, uso un linkedhashmap para mantener orden de insercion
         * y que queden las fechas ordenadas en la vista de usuario */

        final Map<YearMonth, Double> result =
                collect.entrySet().stream() // voy a iterar sobre las entradas del mapa
                        .collect(
                                Collectors.toMap( // quiero hacer un nuevo mapa
                                        Map.Entry::getKey, // la key es la misma
                                        e -> e.getValue() // el value es la suma de los consumos
                                                .stream()
                                                .mapToDouble(Periodo::getConsumo) // convierto los periodos a consumos
                                                .sum(), // y los sumo
                                        (u, v) -> { throw new IllegalStateException(); },
                                        LinkedHashMap::new
                                )
                        );

        return result;
    }
}
