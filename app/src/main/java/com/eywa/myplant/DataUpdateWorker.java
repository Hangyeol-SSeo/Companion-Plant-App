package com.eywa.myplant;

public class DataUpdateWorker {
    /*
    query:
    'SELECT * ' +
        'FROM plant_environment_data ped ' +
        'JOIN plants p ON ped.plant_id = p.plant_id ' +
        'JOIN users u ON p.user_id = u.user_id ' +
        `WHERE u.username = ${username} AND p.plant_name = ${plantname} ` +
        'ORDER BY ped.recorded_time DESC LIMIT 100;'
     */


}
