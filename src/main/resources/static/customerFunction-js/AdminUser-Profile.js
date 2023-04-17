let profile = document.getElementById("subMenu");

function toggleMenu(){
   profile.href = './stylecss/navbar.css';
   profile.classList.toggle("open-menu");
}

let mail = document.getElementById("mailMenu");

function toggleEmailMenu(){
    mail.href = './stylecss/pagedesign.css';
    mail.classList.toggle("open-mail-menu");
}

//close the dropdown table by clicking outside
window.onclick = (e) =>{
  profile.href = './stylecss/navbar.css';

  if(!e.target.matches(".profile-icon")){
      if(profile.classList.contains("open-menu")){
      profile.classList.remove("open-menu");
      }
  }

   if(!e.target.matches(".dropbtn")){
       if(mail.classList.contains("open-mail-menu")){
          mail.classList.remove("open-mail-menu");
       }
    }
}


