//this works
console.log("Running JS");
//and this works
$("#randomText").click(function(){
    //doesn't refresh the page
    $("#random").load("/random");
});

const stringText = document.getElementById("randomStringText");
stringText.onclick = () => {
    const lengthInput = document.getElementById("lengthValue");
    const url = "/randomString/"+lengthInput.value;
    fetch(url).then((response)=>{
        return response.text();
    }).then((responseText)=>{
        const randomString=document.getElementById("randomString");
        randomString.textContent=responseText;  //can also use .innerHTML
    });
};