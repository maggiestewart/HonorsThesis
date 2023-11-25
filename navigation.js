// Function to handle navigation
function navigate(target) {
    if (target === "logo") {
        window.location.href = "index.html";
    } else if (target === "digital"){
        window.location.href = "";
    } else if (target === "services" || target === "outreach" || target === "more"){
        window.location.href = "services.html";
    } else if (target === "navEvents"){
        window.location.href = "events.html";
    } else if (target === "navCatalog" || target  === "catalog"){
        window.location.href = "catalog.html";
    } else if (target === "navAccount" || target === "account") {
        window.location.href = "userLogin.html";
    } else {
        console.log("Navigating to:", target);
    }
}

// Event listeners for navigation bar links
document.getElementById("logo").addEventListener("click", function () {
    navigate("logo");
});
document.getElementById("digital").addEventListener("click", function () {
    navigate("digital");
});
document.getElementById("services").addEventListener("click", function () {
    navigate("services");
});
document.getElementById("navEvents").addEventListener("click", function () {
    navigate("navEvents");
});
document.getElementById("navCatalog").addEventListener("click", function () {
    navigate("navCatalog");
});
document.getElementById("navAccount").addEventListener("click", function () {
    navigate("navAccount");
});

// Event listeners for footer links
document.getElementById("account").addEventListener("click", function () {
    navigate("account");
});
document.getElementById("card").addEventListener("click", function () {
    navigate("card");
});
document.getElementById("catalog").addEventListener("click", function () {
    navigate("catalog");
});
document.getElementById("outreach").addEventListener("click", function () {
    navigate("outreach");
});
document.getElementById("more").addEventListener("click", function () {
    navigate("more");
});
