document.getElementById('create_game_button').addEventListener('click', function() {
    fetch('/api/game/create', {
        method: 'POST'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        const gameId = data.id;
        if (!gameId) {
            alert('An error occurred, please try again later.');
            console.error('No gameId received.');
            return;
        }

        const redirectUrl = `/${gameId}/play`;
        window.location.href = redirectUrl;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred, please try again later.');
    });
});


document.getElementById('join_game_button').addEventListener('click', function() {
    const gameIdInput = document.getElementById('join_game_input');
    const gameId = gameIdInput.value.replace(/\s/g, '');

    if (!gameId) {
        alert('Please enter a valid Game ID.');
        console.error('Game ID is empty.');
        return;
    }

    const redirectUrl = `/${gameId}/play`;

    fetch(redirectUrl, {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        window.location.href = redirectUrl;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred, please try again later.');
    });
});