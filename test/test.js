if(typeof window.iconfall == "undefined"){
    window.iconfall = {};
}

function Iconfall(){
    function createCanvas(div){
        var divElement = document.getElementById(div);
        var canvas = divElement.createElement('canvas');

        canvas.id = "CursorLayer";
        canvas.width = divElement.style.width;
        canvas.height = divElement.style.height;

        document.body.appendChild(canvas);

        var image = new Image();
        image.src = "instagram.png";
    }

    (function Constructor(div){
        console.dir(div);

        createCanvas(div);
    }).apply(this, arguments);
}

iconfall.Iconfall = Iconfall;