

function update_location()
{
    beepOnce();
    if (document.getElementById('rep_type_ch').value==0)
    {
         $("#afui").popup("Select type of Report");
    } 
    
    document.getElementById('visit_location').value=1;
   
        
}
