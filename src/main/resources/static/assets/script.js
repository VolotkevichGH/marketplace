function showPopup() {
  var popup = document.getElementById('popup');
  popup.style.display = 'block';

  // Запустить таймер для автоматического закрытия через 5 секунд
  setTimeout(function() {
    closePopup();
  }, 5000);
}

function closePopup() {
  var popup = document.getElementById('popup');
  popup.style.display = 'none';
}