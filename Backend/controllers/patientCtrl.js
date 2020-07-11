const table = require('../database/models/patient');

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

