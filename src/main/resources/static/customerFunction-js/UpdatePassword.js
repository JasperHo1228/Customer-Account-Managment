const form = document.getElementById('form');
const old_password = document.getElementById('old_password');
const new_password = document.getElementById('new_password');
const re_type_password = document.getElementById('re_type_password');

const togglePassword = document.querySelector('#togglePassword');
const toggleRetypePassword = document.querySelector('#toggleRetypePassword');

  togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = old_password.getAttribute('type') === 'password' ? 'text' : 'password';
    old_password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

 toggleRetypePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = new_password.getAttribute('type') === 'password' ? 'text' : 'password';
    new_password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

const setError = (element, message) => {
          const inputControl = element.parentElement;
          const errorDisplay = inputControl.querySelector('.error');
          errorDisplay.innerText = message;
          inputControl.classList.add('error')
          inputControl.classList.remove('success');
 };

const setSuccess = element => {
              const inputControl = element.parentElement;
              const errorDisplay = inputControl.querySelector('.error');
              errorDisplay.innerText = '';
              inputControl.classList.add('success');
              inputControl.classList.remove('error');
              };

const isValidPassword = password => {
    const passwordCheck =  /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[A-Z])(?=.*[()#!%&\$\.\%\&\@<>\*\-]).{8,20}$/;
    return passwordCheck.test(String(password));
}

function checkOldPassword(inputOldPassword){
     if(old_password.value === ''){
          setError(old_password,"Required your old password");
          event.preventDefault();
     }
     else if(!isValidPassword(old_password.value)){
      setError(old_password,"Must contain at least one number and one uppercase and\n" +
                           "lowercase letter and one special character, and at least 8 or more characters");
                              event.preventDefault();
     }
     else{
         setSuccess(old_password);
      }
}

function checkNewPassword(inputNewPassword){
             if(new_password.value === ''){
                     setError(new_password,'Require your password');
                     event.preventDefault();
                    }
              else if(!isValidPassword(new_password.value)){
                      setError(new_password,"Must contain at least one number and one uppercase and\n" +
                              "lowercase letter and one special character, and at least 8 or more characters");
                       event.preventDefault();
                      }
              else{
                    setSuccess(new_password);
                    }
            }

function checkRetypePassword(RetypePassword){
             if(re_type_password.value === ''){
                    setError(re_type_password,'Please re-type your password');
                     event.preventDefault();
                    }
              else if(re_type_password.value != new_password.value){
                      setError(re_type_password,'Password not Match');
                       event.preventDefault();
                      }
              else{
                    setSuccess(re_type_password);

                    }

            }

  //click event
  function validateInputs(event) {
          checkOldPassword(old_password.value);
          checkNewPassword(new_password.value);
          checkRetypePassword(re_type_password.value);
}