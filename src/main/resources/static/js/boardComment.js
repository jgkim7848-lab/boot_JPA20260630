console.log("boardComment.js in");
//boardComment.js
// cmtAddBtn 버튼을 클릭하면 입력한 댓글과 작성자, bno 값을 controller 전송
document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText');
    const cmtWrtier = document.getElementById('cmtWriter');

    if(cmtText == null || cmtText.value.trim() == ''){
        alert('댓글 입력요망!');
        cmtText.focus();
        return false;
    }
    let cmtData = {
        bno: bno,
        writer: cmtWrtier.innerText,
        content: cmtText.value
    }
    console.log(cmtData);
    registerCommentToServer(cmtData).then(result => {
        if(result == '1'){
            alert("댓글 등록 성공!");
            // 댓글 입력창을 비우고, 포커스 맞추기
            cmtText.value='';
            cmtText.focus();
        }else{
            alert("댓글 등록 실패!");
            cmtText.focus();
        }
        // 댓글 리스트 호출
        spreadCommentList(bno);
    });
});

// list를 화면에 출력하는 함수
function spreadCommentList(bno, page=1) {
    commentListFromServer(bno, page).then(result =>{
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        if(result.content.length > 0){
            // 댓글이 있는 경우
            if(page == 1){
                ul.innerHTML = '';
            }
            let li = '';
            for(let comment of result.content){
                li+=`<li class="list-group-item shadow-sm rounded-2" data-cno="${comment.cno}">`;
                li+=`<div class="ms-2 me-auto"> no. ${comment.cno}`;
                li+=`<div class="fw-bold">${comment.writer}</div>`;
                li+=`${comment.content}`;
                li+=`</div>`;
                li+=`<div class="d-flex justify-content-end gap-2">`;
                li+=`<span class="badge rounded-pill text-bg-primary align-content-center">${comment.regDate}</span>`;
                li+=`<span class="badge rounded-pill text-bg-warning align-content-center">${comment.modDate}</span>`;
                //li+=`<span class="badge rounded-pill text-bg-primary">${comment.regDate.substring(0,10)} ${comment.regDate.substring(comment.regDate.indexOf("T")+1, comment.regDate.lastIndexOf("."))}</span>`;
                li+=`<button type="button" class="btn btn-outline-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#commentModal">%</button>`;
                li+=`<button type="button" class="btn btn-outline-danger btn-sm del">X</button>`;
                li+=`</div>`;
                li+=`</li>`;
            }
            ul.innerHTML += li;

            // page 처리 => moreBtn data-page = +1
            const moreBtn = document.getElementById('moreBtn');
            // 아직 리스트가 더 있다면  버튼 표시
            if(page < result.totalPages){
                moreBtn.style.visibility = "visible";  // 표시
                moreBtn.dataset.page = parseInt(page) + 1;
            }else{
                moreBtn.style.visibility = "hidden"; // 숨김
            }
        }else{
            // 댓글이 없는 경우
            ul.innerHTML = `<li class="list-group-item shadow-sm rounded-2">등록된 댓글이 없습니다.</li>`;
        }
    });
}

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains("mod")){
        // mod 버튼을 클릭하면 수정할 데이터를 modal에 띄우기
        // modal 화면에 보낼 데이터 cno, writer, content

        // closest : 내가 속해있는 내 부모의 객체를 가져오기
        let li = e.target.closest('li');
        let cno = li.dataset.cno;
        let cmtWriter = li.querySelector('.fw-bold').innerText;
        // nextSibling : 같은 부모 밑에 있는 다른 형제 찾기
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cno);
        console.log(cmtWriter);
        console.log(cmtText.nodeValue);

        document.getElementById('cmtWriterMod').innerHTML=
            `no.${cno}  <b>${cmtWriter}</b>`;
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;
        // cmtModBtn => data-cno="" 속성 추가
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);
    }

    if(e.target.id == 'cmtModBtn'){
        // modal 수정 버튼 => {cno, content} => 비동기 전송
        let modData={
            cno: e.target.dataset.cno,
            content: document.getElementById('cmtTextMod').value
        }
        console.log(modData);
        // 비동기 전송
        commentUpdateToServer(modData). then(result => {
            if(result == '1'){
                alert("수정 성공!!");
            }
            // 변경된 댓글 출력
            spreadCommentList(bno);
            // 모달 창 닫기
            document.querySelector(".btn-close").click();
        })
    }

    if(e.target.id == 'moreBtn'){
        // 더보기 버튼을 클릭했을 때 => 남아있는 게시글 5개를 더 출력 => 비동기 호출
        spreadCommentList(bno, parseInt(e.target.dataset.page));
    }

    if(e.target.classList.contains("del")){
        // 삭제버튼 인지
        // cno 값 추출 => closest (내가 속한 내 부모 값 찾기)
        let li = e.target.closest('li');
        let cno = li.dataset.cno;
        commentRemoveToServer(cno).then(result =>{
            if(result == "1"){
                alert("삭제성공!!");
                spreadCommentList(bno);
            }
        })
    }
})



// 전송 async 데이터 보내기
async function registerCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config = {
            method:'post',
            headers : {
                'content-type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };

        const response = await  fetch(url, config);
        const result = await response.text();
        return result;
    }catch (e) {
        console.log(e);
    }
}

// list
async function commentListFromServer(bno, page){
    try{
        const response = await fetch(`/comment/list/${bno}/${page}`);
        const result = await response.json();
        return result;
    }catch (e) {
        console.log(e);
    }
}

// remove
async function commentRemoveToServer(cno){
    try{
        // fetch(url, config)
        const response = await fetch("/comment/remove/"+cno,
            {method:"delete"});
        const result = await response.text();
        return result;
    }catch (e) {
        console.log(e);
    }
}

// update
async function commentUpdateToServer(modData){
    try {
        const url = '/comment/modify';
        const config = {
            method: 'put',
            headers: {
                'content-type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(modData)
        }

        const response = await fetch(url, config);
        const result = await  response.text();
        return result;
    }catch (e) {
        console.log(e);
    }
}


//=========================================

// //boardComment.js
// console.log("board.comment")
//
// //cmtAddBtn 버튼 클릭 시 입력한 댓글과 작성자 bno 값을 controller로 전송함.
// document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
//     const cmtText = document.getElementById('cmtText');
//     const cmtWriter = document.getElementById('cmtWriter');
//
//     if(cmtText == null || cmtText.value.trim() == ''){
//         alert('댓글을 입력하세요.');
//         cmtText.focus();
//         return false;
//     }
//
//     let cmtData = {
//         bno: bno,
//         writer: cmtWriter.innerText,
//         content:cmtText.value
//     }
//     console.log(cmtData);
//     registerCommentToServer(cmtData).then(result=>{
//         if(result=='1'){
//             alert("댓글 등록 성공");
//             //댓글 입력창 비우고 포커스 맞추기
//             cmtText.value='';
//             cmtText.focus();
//         } else{
//             alert("댓글 등록 실패");
//             cmtText.focus();
//         }
//         //댓글 리스트 호출.
//         spreadCommentList(bno);
//     })
//     //댓글 리스트도 불러오기를 이 밑으로 보내오면 된
//
//
// })
//
// //list를 화면에 출력하는 함수
// function spreadCommentList(bno, page=1){
//     commentListFromServer(bno, page).then(result => {
//         console.log(result);
//         const ul = document.getElementById('cmtListArea');
//         if(result.content.length > 0){
//             //댓글이 있는 경우
//             if(page == 1){
//                 ul.innerHTML='';
//             }
//             let li = '';
//             for(let comment of result.content){
//                 li+=`<li class="list-group-item shadow-sm rounded-2" data-cno="${comment.cno}">`;
//                 li+=`<div class="ms-2 me-auto"> no. ${comment.cno}`;
//                 li+=`<div class="fw-bold">${comment.writer}</div>`;
//                 li+=`${comment.content}`;
//                 li+=`</div>`;
//                 li+=`<div class="d-flex justify-content-end gap-2">`;
//                 li+=`<span class="badge rounded-pill text-bg-primary align-content-center">${comment.regDate}</span>`;
//                 li+=`<span class="badge rounded-pill text-bg-warning align-content-center">${comment.modDate}</span>`;
//                 //li+=`<span class="badge rounded-pill text-bg-primary">${comment.regDate.substring(0,10)} ${comment.regDate.substring(comment.regDate.indexOf("T")+1, comment.regDate.lastIndexOf("."))}</span>`;
//                 li+=`<button type="button" class="btn btn-outline-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#commentModal">%</button>`;
//                 li+=`<button type="button" class="btn btn-outline-danger btn-sm del">X</button>`;
//                 li+=`</div>`;
//                 li+=`</li>`;
//             }
//             ul.innerHTML += li;
//             //page처리  moreBtn   data-page=+1
//             //아직 리스트가 더 있다면 버튼 표시.
//             //그리고 버튼이 더 없으면 안보이게 하고.....
//             const moreBtn = document.getElementById('moreBtn');
//             if(page < result.totalPages){
//                moreBtn.style.visibility = "visible"; //visible은 표시
//                 moreBtn.dataset.page = page + 1;
//             } else{
//                 moreBtn.style.visibility = "hidden";
//
//             }
//
//
//         } else{
//             //댓글이 없는 경우
//             ul.innerHTML = `<li class="list-group-item shadow-sm rounded-2">등록된 댓글이 없습니다.</li>`
//
//         }
//     })
// }
//
// document.addEventListener('click', (e)=>{
//     if(e.target.classList.contains("mod")){
//         //mod버튼 클릭시 수정할 데이터를 modal에 띄우기.
//         //modal 화면에 보낼 데이터 cno writer content
//
//         //closest  내가 속해있는 내 부모의 객체 가져오기.
//         let li = e.target.closest('li');
//         let cno = li.dataset.cno;
//
//         let cmtWriter = li.querySelector('.fw-bold').innerText;
//
//         // 얘는 node로 빠진다??
//         let cmtText = li.querySelector('.fw-bold').nextSibling; //같은 부모밑에 있는 다른 형제(같은 div로 묶여있을것) 찾기
//         console.log(cno);
//         console.log(cmtWriter);
//         console.log(cmtText, typeof cmtText);    //댓글에서 %버튼 눌렀을때 f12 눌러서 잘 되나 확인.
//
//         document.getElementById('cmtWriterMod').innerHTML=
//             `no.${cno} <b>${cmtWriter}</b>`;
//         document.getElementById('cmtTextMod').value = cmtText.nodeValue;
//
//         //cmtModBtn => data-cno
//         document.getElementById('cmtModBtn').setAttribute("data-cno", cno);
//         //동기 비동기 데이터 전송???
//
//     }
//     if(e.target.id == "cmtModBtn"){
//         //실제 modal의 수정버튼
//         //비동기전송하면 된도
//         let modData={
//             cno: e.target.dataset.cno,
//             content: document.getElementById('cmtTextMod').value
//         }
//         console.log(modData);
//
//         commentUpdateToServer(modData).then(result =>{
//             if(result =='1'){
//                 alert("수정성공!")
//
//             }
//             spreadCommentList(bno);
//             document.querySelector(".btn-close").click();
//
//         })
//     }
//
//
//
//     if(e.target.id == "moreBtn"){
//         //더보기 버튼을 클릭했을때.
//         spreadCommentList(bno, parseInt(e.target.dataset.page));
//     }
//
//
//     if(e.target.classList.contains("del")){
//         //삭제 버튼을 눌렀다면
//         //cno의 값을 추출해줘야함.
//         //cno값은 li안에 있도르.data-cno=에 있는거인듯??? closest로 찾아야한다.  내가 속한 내 부모값을 찾아주는것.
//         let li = e.target.closest('li');
//         let cno = li.dataset.cno;
//         commentRemoveToServer(cno).then(result =>{
//             if(result=="1"){
//                 alert("삭제 성공!");
//                 spreadCommentList(bno);
//             }
//         })
//
//     }
// })
//
// //전송 async 데이터 보내기
// async function registerCommentToServer(cmtData){
//     try {
//         const url = "/comment/post";
//         const config = {
//             method:'post',
//             headers:{
//                 'content-type': 'application/json; charset=utf-8'
//             },
//             body: JSON.stringify(cmtData)
//         }
//         const response = await fetch(url, config);
//         const result = await response.text();
//         return result;
//
//     } catch (e){
//         console.log(e);
//     }
// }
//
// async  function commentListFromServer(bno, page){
//     try {
//         const response = await fetch(`/comment/list/${bno}/${page}`);
//         const result = await response.json();
//         return result;
//     } catch(e){
//         console.log(e);
//     }
// }
//
// //등록에 대한 비동기   가져오는거에 대한 비동기  뭐가 먼저 발생하는지는 모름.....?????
// //
// //
//
//
// //remove        config의 method에 적어줘야....
// async  function commentRemoveToServer(cno){
//     try {
//         //fetch(url, config)                                                   config의 객체로 method만 적어준것
//         const response = await fetch("/comment/remove/" + cno, {method:"delete"});
//         //이거의 호출은 class가 del인 x버튼을 누르면 호출되는거란말이지   호출한 대상을 cno로 넣어주고.
//         const result = await response.text();
//         return result;
//     } catch(e){
//         console.log(e);
//     }
// }
//
// async function commentUpdateToServer(modData){
//     try{
//         const url = '/comment/modify';
//         const config={
//             method:'put',
//             headers:{
//                 'content-type':'application/json; charset=utf-8'
//             },
//             body:JSON.stringify(modData)
//         };
//
//         const response=await fetch(url,config);
//
//         const result=await response.text();
//
//         return result;
//
//     }catch(e){
//         console.log(e);
//     }
// }
//
//
//
//
//
