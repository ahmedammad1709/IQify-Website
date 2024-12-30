// Show Back to Top Button When Scrolling Down
const backToTopButton = document.getElementById('backToTop');

window.addEventListener('scroll', () => {
    // Check if the page is scrolled past 300px
    if (window.scrollY > 50) {
        backToTopButton.classList.add('show'); // Show the button
    } else {
        backToTopButton.classList.remove('show'); // Hide the button
    }
});

backToTopButton.addEventListener('click', () => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
});

// Scroll to Top Function
// function scrollToTop() {
//     window.scrollTo({
//         top: 0,
//         behavior: 'smooth' // Smooth scrolling effect
//     });
// }
