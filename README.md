<h1 style="text-align: center; color: #06ce7e;">Documentation for "Task Manager App"</h1>
    <table style="width:100%; border: 1px solid red; border-collapse: collapse;">
        <tr>
            <td style="border: 1px solid red;padding:4px;">Primary Language: </td>
            <td style="border: 1px solid red;padding:4px;">Kotlin, XML</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Minimum SDK</td>
            <td style="border: 1px solid red;padding:4px;">21</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Target SDK</td>
            <td style="border: 1px solid red;padding:4px;">34</td>
        </tr>
        <tr>
            <td style="border: 1px solid red;padding:4px;">Architecture Followed</td>
            <td style="border: 1px solid red;padding:4px;">MVVM</td>
        </tr>
    </table>
<hr>
<p>
    This project is made in order to complete assignment on making Task manager app with alarm remainder feature for ITmedicus.
</p>
<p>
    To make this project I followed MVVM architecture which one is very easy to manage and hassle free for code reusability rather than other architecture like MVP or MVC.
    <br>
   <b> In building the project</b>, the challenge was time picking after date picker. Firstly I thought how can I combine both and use in one variable. Then I read on Date() object which one is already familiar for me. I found their is a Calender instance also availabe which ones can be converted to Date() object as well. So I used Calender.getInstance() for combining both.
    <br>
    Another challenge I faced when adding alarm trigger function. But some blog post like medium and youtube channel help me to get out the problem. The broadcast reciever solve my problem. 
     <br>
</p>
<p>
    <strong>Working: </strong> App has two screens, one for displaying list of tasks. and another one is for adding and updating task. I used fragment. Because this app should be single activity project.
    App trigger an alarm when due time has come and user haven't completed his task. Here Notification is set for alarming the user for his pending task. 
    <br>
    <b>Note: </b>This app will not trigger alarm in due date when user already completed his task. 
</p>