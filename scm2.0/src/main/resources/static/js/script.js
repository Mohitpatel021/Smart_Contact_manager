let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});
// function to change the theme
function changeTheme() {
  changePageTheme(currentTheme, "");

  const changeThemeButton = document.querySelector("#theme_change_button");

  changeThemeButton.addEventListener("click", (event) => {
    // console.log("them button clicked");
    let oldTheme = currentTheme;
    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }
    // console.log(currentTheme);
    changePageTheme(currentTheme, oldTheme);
  });
}
// set Theme to localStorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

//get theme to localStorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

function changePageTheme(theme, oldTheme) {
  setTheme(currentTheme);
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }
  document.querySelector("html").classList.add(theme);
  // change the text of the theme chnage button
  document
    .querySelector("#theme_change_button")
    .querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
}
