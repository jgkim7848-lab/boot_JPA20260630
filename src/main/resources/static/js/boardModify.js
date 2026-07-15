console.log("boardModify.js in");

//수정버튼을 클릭하면 title, content만 readOnly 풀기 (readOnly=false)
document.getElementById('modBtn').addEventListener('click',()=>{
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;
    // Form 태그의 submit 역할을 할 수 있는 버튼 생성
    // 수정, 삭제버튼을 지우고, update 버튼 생성
    // <button type="submit" class="btn btn-success">update</button>

    // <button></button>
    let regBtn = document.createElement('button');
    regBtn.setAttribute('type','submit');
    regBtn.setAttribute('id','regBtn');
    regBtn.classList.add('btn', 'btn-success');
    regBtn.innerText="update";

    // form 태그의 가장 마지막 요소로 추가
    document.getElementById('modForm').appendChild(regBtn);

    // 수정, 삭제, 리스트 버튼 지우기
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
    document.getElementById('listBtn').remove();

    // (trigger) file upload 버튼 표시
    document.getElementById('trigger').style.display="block";

    // file-x 버튼 표시
    // style-visibility: hidden => file.style.visibility="visible"
    let fileDelBtn = document.querySelectorAll(".file-x");
    fileDelBtn.forEach(btn => {
        btn.style.visibility="visible";
        // btn 을 클릭하면 비동기로 uuid 보내서 DB상에서 파일 삭제
        btn.addEventListener('click',(e)=>{
            let uuid = e.target.dataset.uuid;
            // 비동기 호출
            fileRemoveToServer(uuid).then(result =>{
                if(result == "1"){
                    // 지워진 그림을 화면에서 삭제
                    e.target.closest('li').remove();
                }
            })
        })
    })
})

async function fileRemoveToServer(uuid){
    try {
        const url = "/board/file/"+uuid;
        const config = {
            method: 'delete'
        }

        const response = await fetch(url, config);
        const result = await response.text();
        return result;
    }catch (e) {
        console.log(e);
    }
}


//==============================


// //boardModify.js
// console.log("boardModify.js");
//
// //수정버튼을  클릭하면 title, contents만 readonly를 풀거임.
// //deatail에서 id t  id c 이거 2개를 조지면된다
//
// document.getElementById('modBtn').addEventListener('click', ()=>{
//     document.getElementById('t').readOnly = false;
//     document.getElementById('c').readOnly = false;
//     //수정 버튼이 적용이 됬으니까....잡다한 버튼 버리고
//     //submit 기능 수행이 가능하게 해야겠지.
//     //수정 삭제 버튼을 없애고 update 버튼을 만들거임.
//
//     //<button id="modbtn" type="submit" class="btn btn-success">update</button>
//     let regBtn = document.createElement('button');
//     regBtn.setAttribute('type','submit');
//     regBtn.classList.add('btn', 'btn-success')
//     regBtn.innerText="update";
//
//     //form 태그의 가장 마지막 요소로 추가해줄거임.
//     document.getElementById('modForm').appendChild(regBtn);
//
//     //수정 삭제 버튼 없애기.
//     document.getElementById('modBtn').remove();
//     document.getElementById('delBtn').remove();
//     document.getElementById('listBtn').remove();
//
// })
//
//
//
//
//
//
