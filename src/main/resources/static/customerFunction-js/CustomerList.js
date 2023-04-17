$('#firstName, #lastName').on('keyup',function(){
          var firstName = $('#firstName').val().toLowerCase();
          var lastName = $('#lastName').val().toLowerCase();
          var data = FilterFunction(firstName, lastName, customers);
        rebuildTable(data);
      });

      function FilterFunction(firstName,lastName, data){
            var filterData = [];
         if (firstName.length > 0 || lastName.length > 0) {
            for(var i = 0; i<data.length; i++){
                var fname = data[i].firstName.toLowerCase();
                var lname = data[i].lastName.toLowerCase();

                 if((firstName.length == 0 || fname.includes(firstName)) &&
                         (lastName.length == 0 || lname.includes(lastName))){
                   filterData.push(data[i]);
                }
                }
               }
            else{
                    filterData = [];
                }
             return filterData;
      }

      function rebuildTable(data){
        var table = document.getElementById('CustomerTable')

        table.innerHTML = `<tr class = "table" >
                                <thead class = "table-color">
                                   <tr>
                                       <th>First Name</th>
                                       <th>Last Name</th>
                                       <th>Email</th>
                                       <th>Phone Number</th>
                                       <th>Actions</th>
                                   </tr>
                                </thead>
                          </tr>`

      if (data.length > 0) {
        for(var i = 0; i<data.length; i++){
            var row = `<tr>
                           <td>${data[i].firstName}</td>
                           <td>${data[i].lastName}</td>
                           <td>${data[i].email}</td>
                           <td>${data[i].phoneNo}</td>
                           <td>
                              <a
                                  class = "button_update"
                                  onclick = "location.href = 'customers/edit/${data[i].id}';">
                                  Update <ion-icon class="icon_update" name="create"></ion-icon></a>

                              <a
                                  class = "button_delete"
                                  onclick = "location.href = 'customers/${data[i].id}';">
                                  Delete <ion-icon class="icon_delete" name="close-circle"></ion-icon></a>
                           </td>
                      </tr>`
                      table.innerHTML += row
                }
                }
                else {
                     table.innerHTML = `<tr class = "table" >
                                        <thead class = "table-color">
                                              <tr>
                                                <th>First Name</th>
                                                <th>Last Name</th>
                                                <th>Email</th>
                                                <th>Phone Number</th>
                                                <th>Actions</th>
                                               </tr>
                                        </thead>
                                        </tr>`
                   }
              }

