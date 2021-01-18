function getUserById() {
    const userIdTextBox = document.getElementById('userIdTextBox');
    const userDataContainer = document.getElementById('userDataContainer');
    const id = userIdTextBox.value;
    fetch('api/user/' + id)
        .then(response => response.json())
        .then(user => userDataContainer.innerHTML = JSON.stringify(user));
}