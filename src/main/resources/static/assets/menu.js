document.addEventListener('DOMContentLoaded', function () {
    function setupMenu(menuContainer) {
        var menuLinks = menuContainer.querySelectorAll('.menu a');
        var imageContainer = menuContainer.querySelector('.png-container img');

        menuLinks.forEach(function (link) {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                var imageUrl = link.getAttribute('data-image');
                imageContainer.src = imageUrl;
            });
        });
    }

    var allMenu = document.querySelector('.AllMenu');
    var allMenu1 = document.querySelector('.AllMenu1');
    var allMenu2 = document.querySelector('.AllMenu2');

    setupMenu(allMenu);
    setupMenu(allMenu1);
    setupMenu(allMenu2);
});
