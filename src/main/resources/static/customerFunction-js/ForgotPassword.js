const form = document.getElementById('form');
const password = document.getElementById('password');
const re_type_password = document.getElementById('re_type_password');

const togglePassword = document.querySelector('#togglePassword');
const toggleRetypePassword = document.querySelector('#toggleRetypePassword');

  togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

 toggleRetypePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const retype = re_type_password.getAttribute('type') === 'password' ? 'text' : 'password';
    re_type_password.setAttribute('type', retype);
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

function checkPassword(inputPassword){
             if(password.value === ''){
                     setError(password,'Require your password');
                     event.preventDefault();
                    }
              else if(!isValidPassword(password.value)){
                      setError(password,"Must contain at least one number and one uppercase and\n" +
                              "lowercase letter and one special character, and at least 8 or more characters");
                       event.preventDefault();
                      }
              else{
                    setSuccess(password);
                    }
            }

function checkRetypePassword(RetypePassword){
             if(re_type_password.value === ''){
                    setError(re_type_password,'Please re-type your password');
                     event.preventDefault();
                    }
              else if(re_type_password.value != password.value){
                      setError(re_type_password,'Password not Match');
                       event.preventDefault();
                      }
              else{
                    setSuccess(re_type_password);

                    }

            }

  //click event
  function validateInputs(event) {
          checkPassword(password.value);
          checkRetypePassword(re_type_password.value);
}


