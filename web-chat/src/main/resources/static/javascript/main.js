var getSignupBtn = document.querySelector("#signup-btn")
var getLoginBtn = document.querySelector("#login-btn");
var getSignupForm = document.querySelector("#singup-form").elements;
var getLoginForm  = document.querySelector("#login-form").elements;

async function postUser(event) {
    event.preventDefault();

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
        console.log("Success: ", result);
    } catch(error) {
        console.log("Error: ", error);
    }
}

async function getUser(event) {
    event.preventDefault();
    
    const body = {
        nickname: getLoginForm["nickname"].value,
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
        console.log("Success: ", result);
    } catch(error) {
        console.log("Error: ", error);
    }
}


getSignupBtn.addEventListener("click", postUser);
getLoginBtn.addEventListener("click", getUser);