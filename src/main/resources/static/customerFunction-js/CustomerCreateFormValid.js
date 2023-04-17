const form = document.getElementById('form');
const firstName = document.getElementById('firstName');
const lastName = document.getElementById('lastName');
const email = document.getElementById('email');
const phoneInputField = document.getElementById('phoneNo');

const phoneInput = window.intlTelInput(phoneInputField, {
      utilsScript:
        "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js",
    });
const phone_msg = document.querySelector("#phone-msg");


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

 const setPhoneError = (element,message) => {
                   const inputControl = element.parentElement;
                   phone_msg.innerHTML = message.fontcolor("#ff3860").fontsize(3);
                   document.getElementById("phone-msg").innerHTML = phone_msg.innerHTML;
                   inputControl.classList.add('msg');
                   inputControl.classList.remove("success");
                   };


  const phoneValid = function(element, message){
                  const code = "+"+phoneInput.selectedCountryData.dialCode;
                  const fullNumber = phoneInput.getNumber();
                  const inputControl = element.parentElement;
                  const phoneNumber = fullNumber.replace(code, '');
                  const ans = code +" "+phoneNumber;
                  const number = ans.replace(/\s+/g, ' ').trim();
                  document.getElementById("phoneNo").value = number;
                  inputControl.classList.add("success");
                  };

phoneInputField.addEventListener("click",removeMsg);

 function removeMsg(){
                  phone_msg.innerHTML = "";
                  document.getElementById("phone-msg").innerHTML = phone_msg.innerHTML;
              }

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
  //change the state when click to another place
 let reset = function() {
           phone_msg.innerHTML = "";
           phone_msg.classList.add("msg");
           };

// check phone number
function checkPhone(inputPhone) {
         reset();
         if(phoneInputField.value === ''){
                setPhoneError(phoneInputField,'Require Phone Number');
                event.preventDefault();
         }
         else if(!phoneInput.isValidNumber()){
                 setPhoneError(phoneInputField,'Invalid phone number');
                 event.preventDefault();
                 }
         else{
                 phoneValid(phoneInputField);
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
          checkPhone(phoneInputField);
}

// submit the form by pressing enter button
form.addEventListener("keypress",function(event){
      if(event.key === "Enter"){
        document.getElementById('submit').focus();
        document.getElementById('submit').click();
      }
});
