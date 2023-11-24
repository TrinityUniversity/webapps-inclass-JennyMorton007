"use strict"

const csrfToken=document.getElementById("csrfToken").value;
const validateRoute=document.getElementById("validateRoute").value;
const createRoute=document.getElementById("createRoute").value;
const deleteRoute=document.getElementById("deleteRoute").value;
const addRoute=document.getElementById("addRoute").value;
const tasksRoute=document.getElementById("tasksRoute").value;
const logoutRoute=document.getElementById("logoutRoute").value;


function login(){
    const username=document.getElementById("loginName").value;
    const password=document.getElementById("loginPass").value;
    fetch(validateRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json','Csrf-Token':csrfToken}, 
		body: JSON.stringify({username,password})                   
	}).then(res => res.json()).then(data => {
        //console.log(data);
        if(data){
            document.getElementById("login-section").hidden=true;
            document.getElementById("task-section").hidden=false;
            document.getElementById("login-message").innerHTML="";
            document.getElementById("create-message").innerHTML="";
            document.getElementById("loginName").value="";
            document.getElementById("loginPass").value="";
            //console.log("loading tasks");
            loadTasks();
        }else{
            document.getElementById("loginName").value="";
            document.getElementById("loginPass").value="";
            document.getElementById("login-message").innerHTML="\tInvalid Username/Password";
            document.getElementById("create-message").innerHTML="";
        }
    });
}

function logout(){
    fetch(logoutRoute).then(res=>res.json()).then(tasks =>{
        document.getElementById("login-section").hidden=false;
        document.getElementById("task-section").hidden=true;
    });
}

function createUser(){
    //console.log("creating user (javascript)");
    const username=document.getElementById("createName").value;
    const password=document.getElementById("createPass").value;
    fetch(createRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json','Csrf-Token':csrfToken}, 
		body: JSON.stringify({username,password})                   
	}).then(res => res.json()).then(data => {
        //console.log(data);
        if(data){
            document.getElementById("login-section").hidden=true;
            document.getElementById("task-section").hidden=false;
            document.getElementById("create-message").innerHTML="";
            document.getElementById("login-message").innerHTML="";
            document.getElementById("createName").value="";
            document.getElementById("createPass").value="";
            //console.log("loading tasks");
            loadTasks();
        }else{
            document.getElementById("createName").value="";
            document.getElementById("createPass").value="";
            document.getElementById("create-message").innerHTML="\tUsername taken";
            document.getElementById("login-message").innerHTML="";
        }
    });
}

function loadTasks(){
    const ul=document.getElementById("task-list");
    ul.innerHTML="";
    //console.log("trying to load tasks");
    fetch(tasksRoute).then(res=>res.json()).then(tasks =>{
        //console.log(tasks);
        for(let i=0;i<tasks.length;++i){
            //console.log("loading a task");
            const li=document.createElement("li");
            const text = document.createTextNode(tasks[i]);
            li.appendChild(text);
            li.onclick = e =>{
                fetch(deleteRoute, { 
                    method: 'POST',
                    headers: {'Content-Type': 'application/json' , 'Csrf-Token': csrfToken }, 
                    body: JSON.stringify(i)                   
                }).then(res => res.json()).then(data => {
                    //console.log("adding task (fetch)");
                    if(data){
                        document.getElementById("task-message").innerHTML="";
                        loadTasks();
                    }else{
                        document.getElementById("task-message").innerHTML="\tDelete Failed (invalid index)";
                    }
                });
            }
            ul.appendChild(li);
        }
    });
}

function addTask(){
    //console.log("add route: "+addRoute);
    let task = document.getElementById("newTask").value;
    fetch(addRoute, { 
		method: 'POST',
		headers: {'Content-Type': 'application/json' , 'Csrf-Token': csrfToken }, 
		body: JSON.stringify(task)                   
	}).then(res => res.json()).then(data => {
        //console.log("adding task (fetch)");
        if(data){
            loadTasks();
            document.getElementById("newTask").value="";
            document.getElementById("task-message").textContent="";
        }else{
            document.getElementById("task-message").textContent="\tAdd Failed";
        }
    });
}
