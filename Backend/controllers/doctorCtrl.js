const table = require('../database/models/doctor');
const fetch = require('../functions/fetchFunction');

module.exports.getDoctorProfile = (req,res)=>{
    var id = req.user.id;
    console.log("User Id",id);

    return table.findAll({
        where:{
            doctor_id:id,
        }
    }).then((userData)=>{
        return res.json({
            success:true,
            profile:userData,
        })
    }).catch((err)=>{
        console.log(err);
        return res.json({
            success:false,
            msg:"Internal Server Error"
        })
    })
}

module.exports.getAllDoctorProfile=(req,res)=>{
    return table.findAll()
    .then((data)=>{
        return res.status(200).json({
            success:true,
            data:data,
        })
    }).catch((err)=>{
        console.log(err);
        res.status(500).json({
            success:false,
            msg:"Internal Server Error",
        })
    })
}

module.exports.updateDoctorProfile = (req,res)=>{
    const id = req.user.id;
    const update = req.body;
    console.log("Requested body",req.body);

    return table.update(update,{where:{doctor_id:id},returning:true})
    .then((updatedUser)=>{
        res.status(200).json({
            success:true,
            user: fetch.include(updatedUser[1][0],[
                "doctor_id",
                "doctor_name",
                "phone_number",
                "email",
                "registration_number",
                "year_of_registration",
                "state_medical_council",
                "photo_path",
                "created_at",
                "updated_at",
                "deleted_at"
            ])
        })
    }).catch((err)=>{
        console.log(err);
        res.status(500).json({
            success:false,
            error:err,
        })
    })
}