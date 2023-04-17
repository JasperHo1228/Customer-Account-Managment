const form = document.getElementById('form');
const firstName = document.getElementById('firstName');
const lastName = document.getElementById('lastName');
const email = document.getElementById('email');

const setError = (element, message) => {
          const inputControl = element.parentElement;
          const errorDisplay = inputControl.querySelector('.error');
          errorDisplay.innerText = message;
          inputControl.classList.add('error');
          inputControl.classList.remove('success');
};

const setSuccess = element => {
              const inputControl = element.parentElement;
              const errorDisplay = inputControl.querySelector('.error');
              errorDisplay.innerText = '';
              inputControl.classList.add('success');
              inputControl.classList.remove('error');
              };

const isValidEmail = email => {
    const emailRegex = /^[a-zA-Z0-9]+([._%+-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(String(email).toLowerCase());
}

const isValidName = firstName => {
     const re = /^[a-zA-Z\s]+$/;
       return re.test(String(firstName).toLowerCase());
}

function checkFirstName(inputFirstName){
             if(firstName.value === ''){
                    setError(firstName,'Require First Name');
                      event.preventDefault();
                    }
              else if(!isValidName(firstName.value)){
                      setError(firstName,'Alphabet only');
                        event.preventDefault();
                      }
              else{
                    setSuccess(firstName);
                    }
            }

function checkLastName(inputLastName){
           if(lastName.value === ''){
                setError(lastName,'Require Last Name');
                event.preventDefault();
            }
            else if(!isValidName(lastName.value)){
                  setError(lastName,'Alphabet only');
                  event.preventDefault();
            }
            else{
                 setSuccess(lastName);
            }
 }


function checkEmail(inputEmail){
          if(email.value === ''){
                  setError(email,'Require Email');
                  event.preventDefault();
          }
          else if(!isValidEmail(email.value)){
                  setError(email,'Wrong Email Format');
                  event.preventDefault();
           }
           else{
                setSuccess(email);
                 }
          }

   //click event
   function validateInputs(event) {
           checkFirstName(firstName.value);
           checkLastName(lastName.value);
           checkEmail(email.value);
   }