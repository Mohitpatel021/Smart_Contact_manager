// let currentTheme = getTheme(); //light theme by default

// document.addEventListener("DOMContentLoaded", () => {
//   changeTheme();
// });
// // function to change the theme
// function changeTheme() {
//   changePageTheme(currentTheme, "");

//   const changeThemeButton = document.querySelector("#theme_change_button");

//   changeThemeButton.addEventListener("click", (event) => {
//     // console.log("them button clicked");
//     let oldTheme = currentTheme;
//     if (currentTheme === "dark") {
//       currentTheme = "light";
//     } else {
//       currentTheme = "dark";
//     }
//     // console.log(currentTheme);
//     changePageTheme(currentTheme, oldTheme);
//   });
// }
// // set Theme to localStorage
// function setTheme(theme) {
//   localStorage.setItem("theme", theme);
// }

// //get theme to localStorage
// function getTheme() {
//   let theme = localStorage.getItem("theme");
//   return theme ? theme : "light";
// }

// function changePageTheme(theme, oldTheme) {
//   //light theme and  blank ""
//   setTheme(currentTheme);
//   if (oldTheme) {
//     document.querySelector("html").classList.remove(oldTheme);
//   }
//   document.querySelector("html").classList.add(theme);
//   // change the text of the theme chnage button
//   document.querySelector("#theme_change_button");
//   // .querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
// }
debugger;
const switchToggle = document.querySelector("#switch-toggle");
let isDarkmode = false;
let currentTheme = getTheme();

const darkIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
</svg>`;

const lightIcon = `<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
</svg>`;

function toggleTheme() {
  let currentTheme = getTheme();
  console.log(currentTheme);
  isDarkmode = !isDarkmode;
  localStorage.setItem("isDarkmode", isDarkmode);
  switchTheme();
}
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
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

//get theme to localStorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
function switchTheme() {
  if (isDarkmode) {
    switchToggle.classList.remove("bg-yellow-500", "-translate-x-2");
    switchToggle.classList.add("bg-gray-700", "translate-x-3");
    setTimeout(() => {
      switchToggle.innerHTML = darkIcon;
    }, 250);
  } else {
    switchToggle.classList.add("bg-yellow-500", "-translate-x-2");
    switchToggle.classList.remove("bg-gray-700", "translate-x-3");
    setTimeout(() => {
      switchToggle.innerHTML = lightIcon;
    }, 250);
  }
}

function changePageTheme(theme, oldTheme) {
  //light theme and  blank ""
  setTheme(currentTheme);
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }
  document.querySelector("html").classList.add(theme);
  // change the text of the theme chnage button
  document.querySelector("#theme_change_button");
  // .querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
}

switchTheme();
