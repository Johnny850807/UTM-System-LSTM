var serverUrl = getServerUrl();
//should check token
if(getToken("UTM-Token") != ""){
    window.location.href="/src/html/main-map.html";
}


const loginFormElement = document.getElementById("login-form");

loginFormElement.addEventListener('submit',function(e) {
    e.preventDefault();
    const loginFormData = new FormData(this);
        
        axios.post(serverUrl+'/account/login', 
        {
            "pilot-id":loginFormData.get('pilot-id'),
            "password":loginFormData.get('password'),
            "retype-password":loginFormData.get('retype-password')
        }
        ).then(function (response) {
            var token = response.data['token'];
            var pilotId = response.data['pilot-id'];

            if(token == "token"){
                saveTokenToCookie('UTM-Token',token);
                saveTokenToCookie('pilot-id',pilotId);
                window.location.href="/src/html/main-map.html";
            }
            console.log(response);
          }).catch(function (error) {
            var errorMessage = error.response.data;
            document.getElementById("password-error-message").style.display="block";
            document.getElementById("password-error-message").textContent = errorMessage['message'];
            document.getElementById("retype-password").style.backgroundColor = "#fcfdc6";
            console.log(errorMessage);
          });
})

