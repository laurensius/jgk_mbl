function keListRamen(){
    JSInterface.keListRamen();
}

function keListMinuman(){
    JSInterface.keListMinuman();
}

function keListMakanan(){
    JSInterface.keListMakanan();
}

function keDetailRamen(id){
    JSInterface.keDetailRamen(id);
}

function keDetailMinuman(id){
    JSInterface.keDetailMinuman(id);
}

function keDetailMakanan(id){
    JSInterface.keDetailMakanan(id);
}

function keListPesanan(){
    JSInterface.keListPesanan();
}



function verifikasi(){
    var teks = document.getElementById("nama_konsumen").value;
    if(teks==="" || teks.length===0){
        document.getElementById("info").innerHTML = '<div class="well">Whoopppsss! Silahkan masukkan nama Kamu!</div>';
    }else{
        JSInterface.savePreferences(teks);
    }
}

function hapusPreferences(){
    JSInterface.deletePreferences();
}