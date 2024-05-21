var getSignupBtn = document.querySelector("#signup-btn")
var getLoginBtn = document.querySelector("#login-btn");
var getSignupForm = document.querySelector("#singup-form").elements;

function sendForm(event) {
    event.preventDefault();

    const bodyObj = {
        nickname: getSignupForm["nickname"].value,
        email: getSignupForm["email"].value,
        password: getSignupForm["password"].value
    }

    postUser(bodyObj);
}

async function postUser(body) {
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


getSignupBtn.addEventListener("click", sendForm);