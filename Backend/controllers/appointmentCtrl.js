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
        console.log(err);
        return res.status(500).json({
            success:true,
            msg:"Internal Server Error",
        })
    })
}

module.exports.addAppointment = (req,res)=>{
    const id = req.user.id;
    //Here id is the patient's id
    const appointment = req.body;
    appointment.patient_id = id;
    console.log("Body",req.body);

    return table.create(appointment,{returning:true})
    .then((appointmentData)=>{
        res.status(200).json({
            success:true,
            appointment:appointmentData
        })
    }).catch((err)=>{
        console.log(err);
        return res.status(500).json({
            success:false,
            msg:"Internal Server Error",
        })
    })
}


module.exports.deleteAppointment = (req,res)=>{
    const id = req.user.id;
    //Here the id is the patient's id

    const appointmentId = req.body.appointment_id;

    return table.destroy({where:{appointment_id :appointmentId}})
    .then(()=>{
        res.status(200).json({
            success:true,
            msg:"Appointment successfully deleted"
        })
    }).catch((err)=>{
        console.log(err);
        res.status(500).json({
            success:false,
            msg:"Internal server error"
        })
    })
}