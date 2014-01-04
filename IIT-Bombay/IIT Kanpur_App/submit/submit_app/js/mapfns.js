/* SELECT YOUR LOCATION AND POST IT ON THE SERVER*/

function before_report_location()
{
    
    $('#map-canvas').data('type',1);
    clearMarkers();
    document.getElementById('visit_1').value=0;
    get_gps_Location();
    
   
}
function get_gps_Location()
{

try
{
    intel.xdk.geolocation.getCurrentPosition(sucGetLoc1,failGetLoc1);
}
catch (err)
{
    alert(err.message);
    alert(err.name);
}
}
function sucGetLoc1(p)
{
        
        var gps_lat=p.coords.latitude;
        var gps_lang=p.coords.longitude;
        $('.MapLon').val(p.coords.longitude);
        $('.MapLat').val(p.coords.latitude);
        var my_latlang=new google.maps.LatLng(gps_lat,gps_lang);
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({
        "latLng":my_latlang
        }, function (results, status) {
        console.log(results, status);
        if (status == google.maps.GeocoderStatus.OK) {
        console.log(results);
        var placeName = results[0].formatted_address;
        //document.getElementById('searchbytext').style.display="block";
        document.getElementById('searchbytext').value=placeName;
        moveMarker("",my_latlang);
        map.setCenter(my_latlang);
        curr_marker.setDraggable(true);}
        google.maps.event.trigger(map, 'resize');
        map.setZoom( map.getZoom() );
   
});
}
function failGetLoc1()
{
    
      $("#afui").popup("Unable to find your current location.");
   
}
function select_location()
{
    beepOnce();
    google.maps.event.trigger(map, 'resize');
    before_report_location();
    document.getElementById('myicon1').className="";
    $("#myicon1").attr("href", "#donor_info");
    $("#myicon1").addClass('icon left');
    $("#myicon1").attr("onClick","dummy()");
    document.getElementById('myicon2').className="";
    $("#myicon2").attr("href", "#donor_info");
    $("#myicon2").addClass('icon check');
    $("#myicon2").attr("onClick","dummy()");
   document.getElementById('myicon3').className="";
    $("#myicon3").attr("href", "#main");
    $("#myicon3").addClass('icon home');
    $("#myicon3").attr("onClick","dummy()");
}


/* END OF POST LOCATION */





/* BEGINNING OF THE MULTIPLE MARK TRIGGER FUNCTION */

 function mutiple_mark_trigger()
{
    beepOnce();
    google.maps.event.trigger(map, 'resize');
   // document.getElementById('searchbytext').style.display="none";
    document.getElementById('myicon1').className="";
    $("#myicon1").attr("href", "#donor_1");
    $("#myicon1").addClass('icon left');
    $("#myicon1").attr("onClick","dummy()");
    document.getElementById('myicon2').className="";
    $("#myicon2").attr("href", "#main");
    $("#myicon2").addClass('icon home');
    $("#myicon2").attr("onClick","dummy()");
    $('#map-canvas').data('type',2);
     document.getElementById('myicon3').className="";
     $("#myicon3").attr("href", "#bank");
    $("#myicon3").addClass('icon new');
    $("#myicon3").attr("onClick","dummy()");
    //alert("function called for nothing");
    showMarkers();
    google.maps.event.trigger(map, 'resize');
    map.setZoom(map.getZoom());
}

/* end of the multiple mark trigger function */





/* SHOW LOCATION FUNCTION OF THE RECENT FEED UPDATES*/  

function show_this_location(lat,lang)
{
    
    $('#map-canvas').data('type', 3);
    clearMarkers();
    var my_latlang=new google.maps.LatLng(lat,lang);
   // document.getElementById('searchbytext').style.display="none";
    moveMarker("",my_latlang);
    map.setCenter(my_latlang);
   
    
}



function viewonmap(b)
{
    beepOnce();
    document.getElementById('searchbytext').value=b.childNodes[0].innerHTML;
    google.maps.event.trigger(map, 'resize');
    map.setZoom( map.getZoom() );
    document.getElementById('myicon1').className="";
    $("#myicon1").attr("href", "#bank");
    $("#myicon1").addClass('icon left');
    $("#myicon1").attr("onClick","dummy()");
    document.getElementById('myicon2').className="";
    $("#myicon2").attr("href", "#main");
    $("#myicon2").addClass('icon home');
    $("#myicon2").attr("onClick","dummy()");
    document.getElementById('myicon3').className="";
    $("#myicon3").attr("href", "#bank");
    $("#myicon3").addClass('icon right');
    $("#myicon3").attr("onClick","dummy()");
    show_this_location(26,80);
    
}


function viewonmap_zone(b)
{
    beepOnce();
    
    //document.getElementById('searchbytext').style.display="none";
   document.getElementById('myicon1').className="";
    $("#myicon1").attr("href", "#recipient1");
    $("#myicon1").addClass('icon left');
    $("#myicon1").attr("onClick","dummy()");
    document.getElementById('myicon2').className="";
    $("#myicon2").attr("href", "#recipient1");
    $("#myicon2").addClass('icon check');
    $("#myicon2").attr("onClick","dummy()");
    document.getElementById('myicon3').className="";
    $("#myicon3").attr("href", "#main");
    $("#myicon3").addClass('icon home');
    $("#myicon3").attr("onClick","updateabfooter6()");
    document.getElementById('visit_2').value=0;
    show_this_location(lat,lng);
    
}


function viewonmap_mine(b)
{
    beepOnce();
    google.maps.event.trigger(map, 'resize');
    map.setZoom( map.getZoom() );
    console.log(b);    
    console.log($(b.childNodes[0]).innerHTML);
     document.getElementById('searchbytext').value=b.childNodes[0].innerHTML;
    console.log($(b.childNodes[0]).data("long"));
    console.log($(b.childNodes[0]).data("lat"));
    var lat = $(b.childNodes[0]).data("lat");
    lng = $(b.childNodes[0]).data("long");
    //document.getElementById('searchbytext').style.display="none";
   document.getElementById('myicon1').className="";
    $("#myicon1").attr("href", "#display_mine");
    $("#myicon1").addClass('icon left');
    $("#myicon1").attr("onClick","dummy()");
    document.getElementById('myicon2').className="";
    $("#myicon2").attr("href", "#main");
    $("#myicon2").addClass('icon home');
    $("#myicon2").attr("onClick","dummy()");
    document.getElementById('myicon3').className="";
    $("#myicon3").attr("href", "#display_mine");
    $("#myicon3").addClass('icon right');
    $("#myicon3").attr("onClick","dummy()");
    document.getElementById('visit_2').value=0;
    show_this_location(lat,lng);
    
}


function dummy()
{
    beepOnce();
}


