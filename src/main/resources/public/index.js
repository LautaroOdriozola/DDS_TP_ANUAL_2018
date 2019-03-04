function getActiveNavbarElementIndex() {
    const pathname = window.location.pathname;

    const filename = R.compose(
        R.nth(0),
        R.split('\.'),
        R.nth(-1),
        R.split('\/')
    )(pathname);

    const elementMap = {
        "hogar": 0,
        "hogares": 0,
        "reportes": 1,
        "transformadores": 1,
        "optimizacion": 1,
        "dispositivos": 2,
        "altaDispositivo": 2
    };

    return R.prop(filename, elementMap);
}

function setActiveNavbarElement() {
    const index = getActiveNavbarElementIndex();

    const elements = document.querySelectorAll('nav a');
    const element = R.nth(index, elements);

    element.classList.add('active-navbar-item');
}

document.addEventListener('DOMContentLoaded', setActiveNavbarElement);