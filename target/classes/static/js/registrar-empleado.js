$(document).ready(function () {
  cargarCargos();
  cargarAreas();
  cargarContratos();
  cargarBancos();
  cargarEps();
  cargarPensiones();
});

async function cargarCargos() {
  const select = document.querySelector("#txtCargo");
  const respuesta = await fetch("/cargar/cargos", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const cargos = await respuesta.json();

  for (let car of cargos) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = car.id;
    nuevaOpcion.text = car.nombre;
    select.add(nuevaOpcion);
  }  
}

async function cargarAreas() {
  const select = document.querySelector("#txtArea");
  const respuesta = await fetch("/cargar/areas", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const areas = await respuesta.json();

  for (let are of areas) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = are.id;
    nuevaOpcion.text = are.nombre;
    select.add(nuevaOpcion);
  }  
}

async function cargarContratos() {
  const select = document.querySelector("#txtTipoContrato");
  const respuesta = await fetch("/cargar/contratos", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const contratos = await respuesta.json();

  for (let con of contratos) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = con.id;
    nuevaOpcion.text = con.nombre;
    select.add(nuevaOpcion);
  } 
}

async function cargarBancos() {
  const select = document.querySelector("#txtBanco");
  const respuesta = await fetch("/cargar/bancos", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const bancos = await respuesta.json();

  for (let ban of bancos) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = ban.id;
    nuevaOpcion.text = ban.nombre;
    select.add(nuevaOpcion);
  } 
}

async function cargarEps() {
  const select = document.querySelector("#txtEps");
  const respuesta = await fetch("/cargar/eps", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const listaeps = await respuesta.json();

  for (let eps of listaeps) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = eps.id;
    nuevaOpcion.text = eps.nombre;
    select.add(nuevaOpcion);
  } 
}

async function cargarPensiones() {
  const select = document.querySelector("#txtPension");
  const respuesta = await fetch("/cargar/pensiones", {
    metod: "GET",
    headres: {
      Accept: "aplication/json",
      "Content-Type": "aplication/json",
    },
  });
  const pensiones = await respuesta.json();

  for (let pen of pensiones) {
    let nuevaOpcion = document.createElement("option");
    nuevaOpcion.value = pen.id;
    nuevaOpcion.text = pen.nombre;
    select.add(nuevaOpcion);
  } 
}


async function registrarEmpleado() {

  let identificacion = document.getElementById('txtIdentificacion').value;  
  let nombres = document.getElementById('txtNombres').value;
  let apellidos = document.getElementById('txtApellidos').value;
  let salario = document.getElementById('txtSalario').value; 
  let cuenta_bancaria = document.getElementById('txtCuenta').value;
  let fecha_ingreso  = new Date();
  let fecha_nac = document.getElementById('txtFecNacimiento').value;
  let estado = 'Activo';
  let telefono  = document.getElementById('txtTelefono').value;
  let correo = document.getElementById('txtEmail').value;

  let sCargo = document.getElementById('txtCargo');
  let cargo_id = sCargo.options[sCargo.selectedIndex].value;  

  let sArea = document.getElementById('txtArea');
  let area_id = sArea.options[sArea.selectedIndex].value; 
  let sTc = document.getElementById('txtTipoContrato');
  let tipo_contrato_id = sTc.options[sTc.selectedIndex].value; 
  let sBanco_id = document.getElementById('txtBanco');
  let banco_id = sBanco_id.options[sBanco_id.selectedIndex].value; 
  let sEps_id = document.getElementById('txtEps');
  let eps_id = sEps_id.options[sEps_id.selectedIndex].value; 
  let sPensiones_id = document.getElementById('txtPension');
  let pensiones_id = sPensiones_id.options[sPensiones_id.selectedIndex].value;

  let datos = {
      "identificacion":identificacion,      
      "nombres":nombres,
      "apellidos":apellidos,
      "salario":salario,
      "cuentaBancaria":cuenta_bancaria,
      "estado":estado,
      "telefono":telefono,
      "correo":correo,
      "fechaIngreso":fecha_ingreso,
      "fechaNac":fecha_nac,
      "cargo":{
        "id":cargo_id
      },
      "area":{
        "id":area_id
      },
      "tipoContrato":{
        "id":tipo_contrato_id
      },
      "banco":{
        "id":banco_id
      },
      "eps":{
        "id":eps_id
      },
      "pensiones":{
        "id":pensiones_id
      },
  };

  const request = await fetch('/personal/registrar-persona', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  if(request.ok){
    alert("¡El empleado fue creado con exito!");
    location.reload();

  }else{
    alert("No se pudo crear al empleado");
  }
}