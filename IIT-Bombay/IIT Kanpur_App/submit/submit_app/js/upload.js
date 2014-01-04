function upload_data()
{
                                    beepOnce();
   
    
                                    var lang= $('.MapLon').val();
                                    var lat=$('.MapLat').val();
                                    var place=document.getElementById('searchbytext').value;
                                    var email=document.getElementById('donor_email').value;
                                    var obj={};
                                        obj['name']=document.getElementById('donor_name').value;
                                        obj['lat']=lat;
                                        obj['lang']=lang;
                                        obj['place']=place;
                                        obj['blood']=document.getElementById('donor_blood').value;
                                        obj['email']=email;
                                        obj['personal']=document.getElementById('donor_personal').value;
                                        obj['contact']=document.getElementById('donor_contact').value;
                                        obj['bday']=document.getElementById('bday').value;
                                        obj['last']=document.getElementById('last_day').value;
                                        obj['uuid']=intel.xdk.device.uuid;
                                        var nj=JSON.stringify(obj);
                                        $.ajax({
                                                type: 'POST',
                                                url: 'http://localhost/recieve.php',
                                                data:{mydata: nj},
                                                success: function(data) 
                                                { 
                                                   
                                                    console.log(data); 
                                                    $("#afui_mask").hide();
                                                   
                                                    $("#afui").popup("Posted successfully");
                                                    
                                                },
                                                error: function( jqXHR, textStatus,errorThrown ) 
                                                {
                                                     $("#afui_mask").hide();
                                                     $("#afui").popup("Cannot Post Report.Check Your Internet Connection!");
                                                },
                                          });
}
                                    


