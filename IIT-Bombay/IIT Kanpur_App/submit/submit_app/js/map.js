//https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false
//http://maps.google.com/maps/api/js?v=3.exp&libraries=places&region=uk&language=en&sensor=false
// CONTROLS ALL THE MAP AND THE MARKER FUNCTIONS AND MAKES A CALL IF DISTANCE EXCEEDS GREATER THAN 200 MILES 
var map,curr_marker;
var markers = [];
var curr_lat=26;
var curr_lang=80;
var last_lat=26;
var last_lang=80;
var curr_place="Nankari , IIT Kanpur";
function initialize() 
{
   // alert("called");            
    var mapOptions = 
                {
                zoom: 8,
                center: new google.maps.LatLng(curr_lat, curr_lang),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                panControl: false,         
                panControlOptions: 
                {
                    position: google.maps.ControlPosition.TOP_RIGHT
                },
                zoomControl: true,
                zoomControlOptions: 
                {
                    style: google.maps.ZoomControlStyle.LARGE,
                    position: google.maps.ControlPosition.TOP_left
                }
                };
                map = new google.maps.Map(document.getElementById('map-canvas'),
                mapOptions);
                 curr_marker = new google.maps.Marker
                 ({
                             position: new google.maps.LatLng(curr_lat, curr_lang) ,
                             map: map,
                             icon: "image/map_blue.png"
                             
                   });
                var input = document.getElementById('searchbytext');
                var autocomplete = new google.maps.places.Autocomplete(input, 
                {
                    types: ["geocode"]
                });
                autocomplete.bindTo('bounds', map);
                google.maps.event.addListener(autocomplete, 'place_changed', function (event) 
                        {
                         var place = autocomplete.getPlace();
                         if (place.geometry.viewport) 
                         {
                             map.fitBounds(place.geometry.viewport);
                         } 
                            else 
                         {
                             map.setCenter(place.geometry.location);
                             map.setZoom(17);
                         }
                         moveMarker(place.name, place.geometry.location);
                         $('.MapLat').val(place.geometry.location.lat());
                         $('.MapLon').val(place.geometry.location.lng());
                     });
    
                google.maps.event.addListener(map, 'center_changed', function() 
                 {
                    window.setTimeout(function() {
                    var dist=distance(last_lat,last_lang,map.getCenter().lat(),map.getCenter().lng());
                    google.maps.event.trigger(map, 'resize');
                    map.setZoom(map.getZoom());
                    if (document.getElementById('visit_1').value==0)
                    {
                    var c_lat = curr_marker.getPosition().lat();
                    var c_lng = curr_marker.getPosition().lng();
                    map.setCenter(new google.maps.LatLng(c_lat,c_lng)); 
                    document.getElementById('visit_1').value=1;
                    }
                    if (document.getElementById('visit_2').value==0)
                    {
                    var c_lat = curr_marker.getPosition().lat();
                    var c_lng = curr_marker.getPosition().lng();
                    map.setCenter(new google.maps.LatLng(c_lat,c_lng)); 
                    document.getElementById('visit_2').value=1;
                    }

                    }, 3000);
               });
    
    
                google.maps.event.addListener(map, 'click', function (event) 
                    {
                     $('.MapLat').val(event.latLng.lat());
                    $('.MapLon').val(event.latLng.lng());
                   // alert("i was clicked");
                    moveMarker("hello", event.latLng);
                    // infowindow.close();
                    var geocoder = new google.maps.Geocoder();
                    geocoder.geocode({
                    "latLng":event.latLng
                    }, function (results, status) {
                    //alert(results, status);
                    if (status == google.maps.GeocoderStatus.OK) {
                    console.log(results);
                    var lat = results[0].geometry.location.lat(),
                    lng = results[0].geometry.location.lng(),
                    placeName = results[0].address_components[0].long_name,
                    latlng = new google.maps.LatLng(lat, lng);
                    //alert( placeName);
                   $("#searchbytext").val(results[0].formatted_address);
                                 }
                             });
                 });
}

google.maps.event.addDomListener(window, 'load', initialize);

function moveMarker(placeName,latlng) 
{
             curr_marker.setIcon("image/map_blue.png");
             curr_marker.setPosition(latlng);
             
}

function addMarker(lat_args,long_args,image,desc_args) 
{
            var marker = new google.maps.Marker(
                        {
                            position:new google.maps.LatLng(lat_args,long_args),
                            map: map,
                            icon:image
                        });
            marker.info = new google.maps.InfoWindow(
            {
                content:desc_args
            });
            google.maps.event.addListener(marker, 'click', function() 
            {
                marker.info.open(map, marker);
            });
            markers.push(marker);
            console.log(markers);
}


// Sets the map on all markers in the array.
function setAllMap(map) 
{
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() 
{
  setAllMap(null);
}


// Shows any markers currently in the array.
function showMarkers() 
{
  setAllMap(map);
  console.log("function show markers called");
}


// Deletes all markers in the array by removing references to them.
function deleteMarkers() 
{
  clearMarkers();
  markers = [];
}


function add_multiple_marker(locations)
{
    
                var image;
                for (i = 0; i < locations.length; i++) 
                {  
                    if (locations[i][3]==1)
                    {
                        image="image/map_red.png";
                        
                    }
                   if (locations[i][3]==2)
                   {
                        image="image/map_green.png";
                   }
                    if (locations[i][3]==3)
                    {
                        image="image/map_yellow.png";
                    }
                    if (locations[i][3]==4)
                    {
                        image="image/map_orange.png";
                    }
                    if (locations[i][3]==5)
                    {
                        image="image/map_pink.png";
                    }
                    addMarker(locations[i][1],locations[i][2],image,locations[i][0]);
                }
    
    
}



function check_mult_marker()
{
    
    var locations=[
        ['india',26,80,1],
        ['pakistan',26,80.8,3],
        ['bangladesh',26.3,80.9,5]
    ];
    
    add_multiple_marker(locations);
}



function check_distance()
{
    alert(distance(26, 80, 26.5208, 80.2302) );
}

    
    function distance(lat1, lon1, lat2, lon2) 
{
    var radlat1 = Math.PI * lat1/180
    var radlat2 = Math.PI * lat2/180
    var radlon1 = Math.PI * lon1/180
    var radlon2 = Math.PI * lon2/180
    return (3959 * Math.acos( Math.cos( radlat1 ) * Math.cos( radlat2 ) * Math.cos( radlon2 - radlon1 ) + Math.sin( radlat1 ) * Math.sin(           radlat2 ) )) 
    
   
}














