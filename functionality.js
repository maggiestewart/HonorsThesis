
// Upcoming Events - home page
function showFeature(feature) {
    const content = document.getElementById('featureContent');
    switch (feature) {
        case 'feature1':
            content.innerHTML = '<h1>Movie on the Plaza</h1>' +
                '<h3>Friday 12/1 @ 6:30pm</h3>' +
                '<p>Join us for an enchanting evening under the stars as we bring the wonders of Disney ' +
                'to the heart of nature. Get ready for a magical movie night that will transport you to a world ' +
                'of imagination all beneath the twinkling canopy of a majestic tree.</p>';
            break;
        case 'feature2':
            content.innerHTML = '<h1>Author Book Talk</h1>' +
                '<h3>Tuesday 12/5 @ 1:30pm</h3>' +
                '<p>Come out to the North branch to meet historical fiction author Lilly McKenzie ' +
                'who will be discussing her book Under the Stars. Light refreshments will be served and books ' +
                'will be signed at the end of the program.</p>';
            break;
        case 'feature3':
            content.innerHTML = '<h1>Craft Night</h1>' +
                '<h3>Monday 12/11 @ 3:30pm</h3>' +
                '<p>Come out to the South branch to learn how to make origami figures!</p>';
            break;
        // Add more cases for additional features
        default:
            content.innerHTML = '<h1>Stay tuned for our upcoming events!</h1>';
    }
}

function filterEvents(category) {
    const events = document.querySelectorAll('.event');

    events.forEach(e => {
        const eventCategory = e.getAttribute('data-category');

        if (category === 'all' || category === eventCategory) {
            e.style.display = 'block';
        } else {
            e.style.display = 'none';
        }
    });
}