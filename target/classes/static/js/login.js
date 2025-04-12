$(document).ready(function() {
   // on ready
});


async function iniciarSesion() {
  let datos = {};
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

 //let user = { 
 //   "email": email, 
 //   "password": password 
 // };

  const request = await fetch('/login/verificar', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });

  const respuesta = await request.text();
  if (respuesta != 'FAIL') {
    localStorage.token = respuesta;
    localStorage.email = datos.email;
    window.location.href = 'index.html'
  } else {
    alert("Las credenciales son incorrectas. Por favor intente nuevamente.");
    document.getElementById('txtEmail').value="";
    document.getElementById('txtPassword').value="";  
  }
}