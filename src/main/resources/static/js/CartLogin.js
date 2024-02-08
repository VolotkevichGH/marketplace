function openNav(menu) {
    if (menu === 'search') {
        document.getElementById(menu + "Sidebar").style.width = "100%";
    } else {
        document.getElementById(menu + "Sidebar").style.width = "50%";
    }
    document.querySelector(".overlay").style.display = "block";
}

function closeNav(menu) {
    document.getElementById(menu + "Sidebar").style.width = "0";
    document.querySelector(".overlay").style.display = "none";
}

window.onclick = function(event) {
    if (event.target.classList.contains("overlay")) {
        closeNav('login');
        closeNav('cart');
        closeNav('search');
    }
}
document.querySelectorAll('.sidebar').forEach(function(el) {
    el.innerHTML += '<button class="closebtn" onclick="closeNav()">×</button>';
});