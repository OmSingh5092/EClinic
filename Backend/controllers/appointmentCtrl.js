const table = require('../database/models/appointment');


module.exports.getAppointment = (req,res)=>{
    const id = req.user.id;
    //Here is will be of the patient
    return table.findAll({
        where:{
            patient_id:id,
        }
    }).then((appointmentData)=>{
        return res.status(200).json({
            success:true,
            appointment:appointmentData,
        })
    }).catch((err)=>{
        return res.status(500).json({
            success:true,
            msg:"Internal Server Error",
        })
    })
}

module.exports.addAppointment = (req,res)=>{
    const id = req.user.id;
    //Here is is the patient's id
    const appointment = req.body.appointment;
    appointment.patient_id = id;

    return table.create(appointment,{returning:true})
    .then((appointmentData)=>{
        res.status(200).json({
            success:true,
            appointment:appointmentData
        })
    }).catch((err)=>{
        return res.status(500).json({
            success:false,
            msg:"Internal Server Error",
        })
    })
}