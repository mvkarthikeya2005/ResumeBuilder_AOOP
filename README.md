# ✨ Resume Builder Pro ✨

<div align="center">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-4285F4?style=for-the-badge&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

*Transform your career journey with our sleek, intuitive resume creation suite*

</div>

<p align="center">
  <img src="https://via.placeholder.com/900x500.png?text=Resume+Builder+Pro+Interface" alt="Resume Builder Pro Interface" width="80%"/>
</p>

## 🚀 Experience the Future of Resume Building

**Resume Builder Pro** isn't just an application—it's your personal career companion. Create stunning, professional resumes in minutes with our award-worthy design and seamless user experience.

> "The resume builder that transformed my job application process!" — Happy User

## ✨ Feature Showcase

<details open>
<summary><b>🎨 Visually Stunning Interface</b></summary>
<br>
<ul>
<li>🌈 <b>Gradient-rich UI</b> with dynamic color transitions</li>
<li>🌙 <b>Smart Dark Mode</b> that adjusts to your system preferences</li>
<li>📱 <b>Responsive design</b> that looks perfect on any display</li>
<li>🎭 <b>Animated transitions</b> for a delightful user experience</li>
</ul>
</details>

<details>
<summary><b>📝 Intelligent Resume Creation</b></summary>
<br>
<ul>
<li>🧙‍♂️ <b>Step-by-step wizard</b> that guides you through each section</li>
<li>✨ <b>Real-time suggestions</b> to improve your content</li>
<li>🔄 <b>Live preview</b> that updates as you type</li>
<li>📊 <b>Progress tracker</b> to visualize completion status</li>
<li>💡 <b>Smart templates</b> that adapt to your career field</li>
</ul>
</details>

<details>
<summary><b>🛠️ Powerful Resume Management</b></summary>
<br>
<ul>
<li>🔍 <b>Advanced search</b> with filters and tags</li>
<li>🔒 <b>Secure cloud sync</b> to never lose your work</li>
<li>📤 <b>Multi-format export</b>: PDF, DOCX, TXT, and more</li>
<li>🖨️ <b>Print optimization</b> with preview and paper size options</li>
<li>📋 <b>Version history</b> to track changes over time</li>
</ul>
</details>

<details>
<summary><b>👥 Comprehensive User Management</b></summary>
<br>
<ul>
<li>🔐 <b>Secure authentication</b> with password recovery</li>
<li>👑 <b>Admin dashboard</b> with user analytics</li>
<li>🧩 <b>Role-based access</b> for different permission levels</li>
<li>🔄 <b>Seamless profile syncing</b> across devices</li>
</ul>
</details>

## 🛣️ Quick Start Journey

<p align="center">
  <img src="https://via.placeholder.com/800x100.png?text=Installation+Journey" alt="Installation Journey" width="90%"/>
</p>

### 📋 Before You Begin

- Java 8+ (Experience the magic of Java)
- MySQL 5.7+ (Where your data lives safely)
- 100MB free space (Room for your career dreams)

### 🧙‍♂️ Database Enchantment

```bash
# Let the magic begin!
mysql -u root -p < database_setup.sql

# Customize your magical connection
# In src/db.properties:
db.url=jdbc:mysql://localhost:3306/resume
db.user=your_mysql_username
db.password=your_secret_password
```

### 🚀 Launch Options

<table>
  <tr>
    <th>For Wizards (IDE)</th>
    <th>For Sorcerers (Command Line)</th>
  </tr>
  <tr>
    <td>
      1. Import the magical project<br>
      2. Ensure the MySQL connector is in your spell book (classpath)<br>
      3. Cast <code>LandingPage.java</code> to begin
    </td>
    <td>
      <code>javac -cp .:lib/* src/*.java -d bin/</code><br><br>
      <code>java -cp bin:lib/* LandingPage</code>
    </td>
  </tr>
</table>

#### Windows Command Line Compilation & Execution

```bash
# Compile all Java files
javac -cp ".;../lib/mysql-connector-java-8.0.13.jar" -d ../bin *.java

# Run different components of the application
# Launch the main application
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" LandingPage

# Other entry points
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" SignUp
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" LoginPage
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" ResumeUI
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" AdminPanel
java -cp "../bin;../lib/mysql-connector-java-8.0.13.jar" ResumeViewer
```

## 💫 User Experience Journey

<p align="center">
  <img src="https://via.placeholder.com/800x300.png?text=Resume+Builder+Workflow" alt="Resume Builder Workflow" width="90%"/>
</p>

### 🧙‍♂️ For Career Wizards

1. **Begin Your Journey** - The enchanting landing page welcomes you
2. **Create Your Identity** - Register or sign in to your magical account
3. **Craft Your Story** - Navigate through beautifully designed sections:
   - 🏆 **Personal Quest** - Share your contact details and personal summary
   - ⚔️ **Skills Arsenal** - Showcase your powerful abilities with custom ratings
   - 🏰 **Knowledge Castles** - Detail your educational strongholds
   - ⚒️ **Adventure Log** - Chronicle your professional quests
4. **Preview Your Scroll** - Witness your resume take shape in real-time
5. **Share Your Legend** - Export, print, or save your professional story

### 👑 For Admin Enchanters

Access the admin portal with the sacred credentials:
- Wizard name: `admin`
- Spell key: `admin123`

Command the realm with powers to:
- 👁️ Observe all registered users in your crystal ball
- ✨ Conjure new user accounts with a wave of your cursor
- 🔮 Modify existing users with transformation spells
- 💥 Banish unwanted accounts when necessary

## 🏗️ Architectural Blueprint

- `src/` - Source code files
  - `created_resumes/` - Storage for generated resumes
  - `util/` - Utility classes including UI constants
  - `*.java` - Main application classes
- `lib/` - External libraries including MySQL connector
- `database_setup.sql` - Database setup script

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add some amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📜 License

This project is open-source and available under the MIT License.

## 🙏 Credits

Created as part of Advanced Object-Oriented Programming project.

---

<div align="center">
  Made with ❤️❤️❤️🌸🌸🌸 of AOOP Team
</div>
