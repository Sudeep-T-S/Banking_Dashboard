import React from 'react';
import '../../styles/Sidebar.css';
import sidebarIcons from '../../assets/sidebar-icons.png';

function Sidebar() {
  return (
    <aside className="sidebar">
      <img src={sidebarIcons} alt="Sidebar Icons" className="sidebar-icons" />
      {/* Add nav links or icons here as your app grows */}
    </aside>
  );
}

export default Sidebar;
