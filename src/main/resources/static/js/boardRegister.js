console.log("boardRegister.js in");

document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('file').click();

})

const regExp = new RegExp("\.(exe|sh|bat|dll|jar|msi)$"); //이런것들은 바이러스 위험이 있으므로 배제하게 하는것.
//실행파일 막기, 10mb이상 사이즈
const maxSize = 1024 * 1024 * 10;

function fileValid(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }//false가 아닌 0으로 주는 이유는???   위의 regExp masSize 듈다 통과해야해서??? 하나라도 valid 통과 못하면 못넣게 막을려고???
    else if(fileSize > maxSize){
        return 0;
    } else{
        return 1;
    }

}

document.getElementById('file').addEventListener('change', (e) => {
    const fileObject = e.target.files;
    console.log(fileObject);

    const div = document.getElementById('fileZone');
    div.innerHTML = '';

    let ul = `<ul class="list-group list-group-flush">`;
    let isOk = 1;

    for (let file of fileObject) {
        let valid = fileValid(file.name, file.size);
        isOk *= valid;
        ul += `<li class="list-group-item">`;
        ul += `${valid
            ? '<div class="fw-bold mb-2">업로드 가능</div>'
            : '<div class="fw-bold mb-2 text-danger">업로드 불가능</div>'}`;
        ul += `${file.name} `;
        ul += `<span class="badge rounded-pill text-bg-${valid ? 'success' : 'danger'}">${file.size} Byte</span>`;
        ul += `</li>`;
    }

    ul += `</ul>`;
    div.innerHTML = ul;

    if(isOk===0){
        //파일중 하나라도 검증을 통과 못했다면
        document.getElementById('regBtn').disabled=true;
    }
});
