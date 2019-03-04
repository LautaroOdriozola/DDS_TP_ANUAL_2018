function getActiveNavbarElementIndex() {
    const pathname = window.location.pathname;

    const filename = R.compose(
        R.nth(0),
        R.split('\.'),
        R.nth(-1),
        R.split('\/')
    )(pathname);

    const elementMap = {
        "index": 0,
        "reportes": 1,
        "alta": 2
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