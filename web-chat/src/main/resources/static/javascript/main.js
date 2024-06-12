var getSignupBtn = document.querySelector("#signup-btn")
var getLoginBtn = document.querySelector("#login-btn");
var getSignupForm = document.querySelector("#singup-form").elements;
var getLoginForm  = document.querySelector("#login-form").elements;
var getSignupDanger  = document.querySelector("#signup-danger");
var getLoginDanger  = document.querySelector("#login-danger");
var getLoginDangerText  = document.querySelector("#login-danger-text");
var getSignupDangerText  = document.querySelector("#signup-danger-text");

var bearerToken = null;

async function postUser(event) {
    event.preventDefault();
    getSignupDanger.classList.add("hidden");
    getLoginDanger.classList.add("hidden");

    const body = {
        nickname: getSignupForm["nickname"].value,
        email: getSignupForm["email"].value,
        password: getSignupForm["password"].value
    }

    try {
        const response = await fetch("/user/save", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        })

        const result = await response.json();

        if (result["code"] != 201) {
            getSignupDangerText.innerHTML = result["message"];
            getSignupDanger.classList.remove("hidden");
        } 
    } catch(error) {
        console.log("Error: ", error);
    }
}

async function getUser(event) {
    event.preventDefault();

    var username = getLoginForm["nickname"].value;

    getSignupDanger.classList.add("hidden");
    getLoginDanger.classList.add("hidden");
    
    const body = {
        nickname: username,
        password: getLoginForm["password"].value
    }

    try {
        const response = await fetch("/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        })

        const result = await response.json();

        if (result["code"] != 200) {
            getLoginDangerText.innerHTML = result["message"];
            getLoginDanger.classList.remove("hidden");
        } else {
            localStorage.setItem("username", username);
            localStorage.setItem("token", result["token"]);
            window.location.replace("/rooms");
        
        }
    } catch(error) {
        console.log("Error: ", error);
    }
}

getSignupBtn.addEventListener("click", postUser);
getLoginBtn.addEventListener("click", getUser);