const BASE_URL = 'http://localhost:8080/api/addSong';
const allSongs = document.getElementById('allSongs');

function addSong(event) {
    event.preventDefault();
    const artist = document.getElementById('artist').value;
    const song = document.getElementById('song').value;
    const genre = document.getElementById('genre').value;
    const data = { artist, song, genre };
    console.log("Data to send:", data);

    fetch(BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(song => {
            const songDiv = document.createElement('div');
            songDiv.innerHTML = `
                        <p>${song.artist} - ${song.song} (${song.genre}) - Song Succesfully added!</p>
                    `;

            // Append the new songDiv to allSongs
            allSongs.appendChild(songDiv);

            // Clear the input fields
            document.getElementById('artist').value = '';
            document.getElementById('song').value = '';
            document.getElementById('genre').value = '';

        })
        .catch(error => console.error(error));
}


function redirectToArtist(event) {
    event.preventDefault();
    const artist = document.getElementById('searchArtist').value;
    window.location.href = `http://localhost:8080/api/artist/${artist}`;
}

function searchSongsByGenre(event) {
    event.preventDefault();
    const genre = document.getElementById('searchGenre').value;
    window.location.href = `http://localhost:8080/api/genre/${genre}`;
}
