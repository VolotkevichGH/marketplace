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