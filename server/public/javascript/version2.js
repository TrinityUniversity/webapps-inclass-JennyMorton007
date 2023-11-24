//console.log("test")
const csrfToken=$("#csrfToken").val();
const loginRoute=$("#loginRoute").val();
const validateRoute=$("#validateRoute").val();
const createRoute=$("#createRoute").val();
$("#content").load(loginRoute);

function login(){
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    $.post(validateRoute,{username,password,csrfToken},data=>{
        $("#content").html(data);
    });
}

function createUser(){
    const username = $("#createName").val();
    const password = $("#createPass").val();
    $.post(createRoute,{username,password,csrfToken},data=>{
        $("#content").html(data);
    });
}

function deleteTask(index){
    $("#content").load("/delete2?index="+index);
}

function addTask(){
    const task = $("#newTask").val();
    console.log(task);
    $("#content").load("/add2?task="+encodeURIComponent(task));
}