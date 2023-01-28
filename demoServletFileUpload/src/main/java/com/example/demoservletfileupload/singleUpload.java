package com.example.demoservletfileupload;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "singleUpload", value = "/singleUpload")
@MultipartConfig(
        location = "",
        fileSizeThreshold = 1024*1024, //1MB
        maxFileSize = 1024*1024*10, //10MB
        maxRequestSize = 1024*1024*20 //20MB

)
public class singleUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String description = request.getParameter("description");
        String message = "";
        try{
            Part filePart = request.getPart("sFile");
            filePart.write(getFileName(filePart));
            String fileName = filePart.getSubmittedFileName();
            message = "Your File \""+fileName+"\" is Uploaded Successfully!";
        }catch (Exception ex){
            message = "Error Uploading File: "+ex.getMessage();
        }
        request.setAttribute("message",message);
        request.getRequestDispatcher("message.jsp").forward(request,response);
    }
    private String getFileName(Part part){
        String contentDisposition = part.getHeader("content-disposition");
        if(!contentDisposition.contains("filename="))
            return null;
        int beginIndex = contentDisposition.indexOf("filename=")+10;
        int endIndex = contentDisposition.length()-1;
        return contentDisposition.substring(beginIndex,endIndex);
    }
}
