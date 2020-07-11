const table = require('../database/models/patient');
const fetch = require('../functions/fetchFunction');
//Get Controller

module.exports.getPatientProfile = (req,res)=>{
    const id = req.user.id;

    return table.findAll({
        where:{
            patient_id:id,
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

module.exports.updatePatientProfile = (req,res)=>{
    const id = req.user.id;
    const update = req.body;
    console.log("Requested body",req.body);

    return table.update(update,{where:{patient_id:id},returning:true})
    .then((updatedUser)=>{
        res.status(200).json({
            success:true,
            user: fetch.include(updatedUser[1][0],[
                "patient_id",
                "patient_name",
                "phone_number",
                "email",
                "blood",
                "gender",
                "weight",
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

