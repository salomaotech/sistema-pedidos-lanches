function menuTopNavFunction() {

    var elementMenu = document.getElementById("menuTopNav");

    if (elementMenu.className === "topMenu") {

        elementMenu.className += " responsive";

    } else {

        elementMenu.className = "topMenu";

    }

}