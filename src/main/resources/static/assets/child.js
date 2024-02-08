    function showScrollTopButton() { 
        var button = document.getElementById("scrollToTopBtn"); 
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) { 
            button.style.display = "block"; 
        } else { 
            button.style.display = "none"; 
        } 
    } 
 
    function scrollToTop() { 
        document.body.scrollTop = 0; 
        document.documentElement.scrollTop = 0; 
    } 
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

document.addEventListener('DOMContentLoaded', function () {
  var menuItems = document.querySelectorAll('.AllMenu .menu .a');
  var dwsMenus = document.querySelectorAll('.AllMenu .dws-menu1, .AllMenu .dws-menu2, .AllMenu .dws-menu3, .AllMenu .dws-menu4, .AllMenu .dws-menu5, .AllMenu .dws-menu6, .AllMenu .dws-menu7, .AllMenu .dws-menu8, .AllMenu .dws-menu9, .AllMenu .dws-menu10');

  function hideAllMenus() {
    dwsMenus.forEach(function (menu) {
      menu.style.display = 'none';
    });
  }

  menuItems.forEach(function (item, index) {
    item.addEventListener('click', function () {
      hideAllMenus();

      var targetMenu;
      if (index === 1) { 
        targetMenu = document.querySelector('.AllMenu .dws-menu2');
      } else {
        targetMenu = document.querySelector('.AllMenu .dws-menu' + (index + 1));
      }

      if (targetMenu) {
        targetMenu.style.display = 'flex';
      }
    });
  });
});


document.addEventListener('DOMContentLoaded', function () {
  var menuItems = document.querySelectorAll('.AllMenu1 .menu .a');
  var dwsMenus = document.querySelectorAll('.AllMenu1 .dws-menu1, .AllMenu1 .dws-menu2, .AllMenu1 .dws-menu3, .AllMenu1 .dws-menu4, .AllMenu1 .dws-menu5, .AllMenu1 .dws-menu6');

  function hideAllMenus() {
    dwsMenus.forEach(function (menu) {
      menu.style.display = 'none';
    });
  }

  menuItems.forEach(function (item, index) {
    item.addEventListener('click', function () {
      hideAllMenus();

      var targetMenu;
      if (index === 1) { 
        targetMenu = document.querySelector('.AllMenu1 .dws-menu2');
      } else {
        targetMenu = document.querySelector('.AllMenu1 .dws-menu' + (index + 1));
      }

      if (targetMenu) {
        targetMenu.style.display = 'flex';
      }
    });
  });
});


document.addEventListener('DOMContentLoaded', function () {
  var menuItems = document.querySelectorAll('.AllMenu2 .menu .a');
  var dwsMenus = document.querySelectorAll('.AllMenu2 .dws-menu1, .AllMenu2 .dws-menu2, .AllMenu2 .dws-menu3, .AllMenu2 .dws-menu4, .AllMenu2 .dws-menu5');

  function hideAllMenus() {
    dwsMenus.forEach(function (menu) {
      menu.style.display = 'none';
    });
  }

  menuItems.forEach(function (item, index) {
    item.addEventListener('click', function () {
      hideAllMenus();

      var targetMenu;
      if (index === 1) { 
        targetMenu = document.querySelector('.AllMenu2 .dws-menu2');
      } else {
        targetMenu = document.querySelector('.AllMenu2 .dws-menu' + (index + 1));
      }

      if (targetMenu) {
        targetMenu.style.display = 'flex';
      }
    });
  });
});


document.addEventListener("DOMContentLoaded", function() {
    let womenElement = document.querySelector(".women");
    let closeMenuElement = document.querySelector(".closeMenu");
    let menuWomenMenElement = document.querySelector(".MenuWomenMen");
    if(womenElement) {
        womenElement.addEventListener("click", function() {
            menuWomenMenElement.classList.add("active");
        });
    }
    if(closeMenuElement) {
        closeMenuElement.addEventListener("click", function() {
            menuWomenMenElement.classList.remove("active");
        });
    }
});

document.addEventListener("DOMContentLoaded", function() { 
    let closeMenuElement = document.querySelector(".closeMenu");
    if(closeMenuElement) {
        closeMenuElement.addEventListener("click", function() {
            console.log("Close menu clicked");
            document.querySelector(".MenuWomenMen").style.display = "none";
        });
    }
});


document.addEventListener("DOMContentLoaded", function() {
    let womenMenuElement = document.querySelector(".womenMenu");
    let closeMenuElement = document.querySelector(".closeMenu");
    let menuWomenMenElement = document.querySelector(".MenuWomenMen");
    if(womenMenuElement) {
        womenMenuElement.addEventListener("click", function() {
            menuWomenMenElement.classList.add("active");
        });
    }
    if(closeMenuElement) {
        closeMenuElement.addEventListener("click", function() {
            menuWomenMenElement.classList.remove("active");
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
        var menLink = document.querySelector('.men');
        var menuBlock = document.querySelector('.MenuWomenMen1');

        menLink.addEventListener('click', function () {
            menuBlock.style.display = (menuBlock.style.display === 'none' || menuBlock.style.display === '') ? 'flex' : 'none';
            
        });
    });

document.addEventListener('DOMContentLoaded', function () {
        var menLink = document.querySelector('.childrenchildren');
        var menuBlock = document.querySelector('.MenuWomenMen2');

        menLink.addEventListener('click', function () {
            menuBlock.style.display = (menuBlock.style.display === 'none' || menuBlock.style.display === '') ? 'flex' : 'none';
            
        });
    });

$('input').focus(function(){
  $(this).attr('placeholder', '');
});

document.addEventListener("DOMContentLoaded", function() {
    let eyeicon = document.getElementById("eyeicon");
    let password = document.getElementById("password");

    eyeicon.addEventListener("click", () => {
        if (password.type == "password") {
            password.type = "text";
            eyeicon.src = "https://i.imgur.com/jLPLH0F.png";
        } else {
            password.type = "password";
            eyeicon.src = "https://i.imgur.com/3g2X07N.png";
        }
    });
});




document.addEventListener('DOMContentLoaded', (event) => {
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("myBtn");
    var span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        modal.style.display = modal.style.display === "none" ? "flex" : "none";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    var women = document.getElementsByClassName("women")[0];
    var menuWomenMen = document.getElementsByClassName("MenuWomenMen")[0];
    var closeMenu = document.getElementsByClassName("closeMenu")[0];

    women.onclick = function() {
        if (menuWomenMen.style.display === "" || menuWomenMen.style.display === "none") {
            menuWomenMen.style.display = "block";
        } else {
            menuWomenMen.style.display = "none";
        }
    }

    closeMenu.onclick = function() {
        menuWomenMen.style.display = "none";
    }
});


document.addEventListener('DOMContentLoaded', () => {
    const womenLink = document.querySelector('.women');
    const menuWomenMen = document.querySelector('.MenuWomenMen');
    const closeMenuButton = document.querySelector('.closeMenu');

    womenLink.addEventListener('click', () => {
        if (menuWomenMen.style.display === 'flex') {
            menuWomenMen.style.display = 'none';
        } else {
            menuWomenMen.style.display = 'flex';
        }
    });

    closeMenuButton.addEventListener('click', () => {
        menuWomenMen.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target === menuWomenMen) {
            menuWomenMen.style.display = 'none';
        }
    });
});







document.querySelector('.men').addEventListener('click', function() {
    document.querySelector('.AllMenu2').style.display = 'block';
    document.querySelector('.AllMenu1').style.display = 'none';
});

document.querySelector('.womenMenu').addEventListener('click', function() {
    document.querySelector('.AllMenu1').style.display = 'block';
    document.querySelector('.AllMenu2').style.display = 'none';
});


    let searchInput = document.querySelector('#searchInput');


    let categoryButtons = document.querySelectorAll('.category');


    searchInput.addEventListener('input', searchItems);


    categoryButtons.forEach(function(button) {
        button.addEventListener('click', function() {

            if (this.classList.contains('selected')) {
                this.classList.remove('selected');
                searchInput.removeAttribute('data-category');
            } else {
                categoryButtons.forEach(function(btn) {
                    btn.classList.remove('selected');
                });
                this.classList.add('selected');
                searchInput.setAttribute('data-category', this.getAttribute('data-category'));
            }
            searchItems();
        });
    });

    function searchItems() {

        let query = searchInput.value;


        let selectedCategory = searchInput.getAttribute('data-category');


        let items = document.querySelectorAll('.item');


        if (query === '') {
            items.forEach(function(item) {
                item.style.display = 'none';
            });
        } else {

            let count = 0;
            items.forEach(function(item) {
                let itemName = item.querySelector('.item-name').textContent;
                let itemCategory = item.getAttribute('data-category');
                if (itemName.startsWith(query) && (!selectedCategory || itemCategory === selectedCategory)) {
                    if (count < 6) {
                        item.style.display = 'flex';
                        count++;
                    } else {
                        item.style.display = 'none';
                    }
                } else {
                    item.style.display = 'none';
                }
            });
        }
    }

         document.addEventListener("DOMContentLoaded", function() {

        var menuWomenMen1 = document.querySelector('.MenuWomenMen1');


        menuWomenMen1.style.display = 'none';

        document.querySelector('.closeMenu1').addEventListener('click', function() {

            menuWomenMen1.style.display = (menuWomenMen1.style.display === 'none' || menuWomenMen1.style.display === '') ? 'block' : 'none';
        });


        document.querySelector('.mennMenu').addEventListener('click', function() {

            menuWomenMen1.style.display = 'none';



        });
    });



         document.addEventListener("DOMContentLoaded", function() {

        var menuWomenMen1 = document.querySelector('.MenuWomenMen2');


        menuWomenMen1.style.display = 'none';

        document.querySelector('.closeMenu6').addEventListener('click', function() {

            menuWomenMen1.style.display = (menuWomenMen1.style.display === 'none' || menuWomenMen1.style.display === '') ? 'block' : 'none';
        });


        document.querySelector('.mennMenu').addEventListener('click', function() {

            menuWomenMen1.style.display = 'none';



        });
    });
