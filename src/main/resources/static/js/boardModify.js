//boardModify.js
console.log("boardModify.js");

//수정버튼을  클릭하면 title, contents만 readonly를 풀거임.
//deatail에서 id t  id c 이거 2개를 조지면된다

document.getElementById('modBtn').addEventListener('click', ()=>{
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;
    //수정 버튼이 적용이 됬으니까....잡다한 버튼 버리고
    //submit 기능 수행이 가능하게 해야겠지.
    //수정 삭제 버튼을 없애고 update 버튼을 만들거임.

    //<button id="modbtn" type="submit" class="btn btn-success">update</button>
    let regBtn = document.createElement('button');
    regBtn.setAttribute('type','submit');
    regBtn.classList.add('btn', 'btn-success')
    regBtn.innerText="update";

    //form 태그의 가장 마지막 요소로 추가해줄거임.
    document.getElementById('modForm').appendChild(regBtn);

    //수정 삭제 버튼 없애기.
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
    document.getElementById('listBtn').remove();

})






