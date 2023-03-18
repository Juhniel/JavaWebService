const BASE_URL = 'http://localhost:8080/api/songs';
const allSongs = document.getElementById('allSongs');

function getAllSongs() {
    fetch(BASE_URL)
        .then(response => response.json())
        .then(data => {
            allSongs.innerHTML = '';
            data.forEach(song => {
                const songDiv = document.createElement('div');
                songDiv.innerHTML = `
                            <h3>${song.artist} - ${song.song}</h3>
                            <button onclick="deleteSong('${song.id}')">Delete</button>`;
                allSongs.appendChild(songDiv);
            });
        })
        .catch(error => console.error(error));
}

function addSong(event) {
    event.preventDefault();
    const artist = document.getElementById('artist').value;
    const song = document.getElementById('song').value;
    const data = { artist, song };
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
                        <h3>${song.artist} - ${song.song}</h3>
                        <button onclick="deleteSong('${song.id}')">Delete</button>
                    `;
            allSongs.appendChild(songDiv);
        })
        .catch(error => console.error(error));
}

function deleteSong(songId) {
    fetch(`${BASE_URL}/${songId}`, { method: 'DELETE' })
        .then(response => {
            if (response.status === 204) {
                const songDiv = document.querySelector(`[onclick="deleteSong('${songId}')"]`).parentElement;
                songDiv.remove();
            }
        })
        .catch(error => console.error(error));
}