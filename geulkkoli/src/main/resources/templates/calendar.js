const today = new Date();
const currentMonth = today.getMonth();
const currentYear = today.getFullYear();

const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0);

const calendar = document.querySelector('.calendar');

const writtenPosts = [
  {
   
    createdAt: '2023-05-13T10:30:00'
    
  },
  {
    
    createdAt: '2023-05-16T10:30:00'
    
  },
  // Add more dummy posts if needed
];


for (let date = new Date(firstDayOfMonth); date <= lastDayOfMonth; date.setDate(date.getDate() + 1)) {
  const day = document.createElement('div');
  day.className = 'day';
  day.textContent = date.getDate();

  if (isWrittenAboutDate(date)) {
    day.classList.add('active');
  } else {
    day.classList.add('inactive');
  }

  calendar.appendChild(day);
}

function isWrittenAboutDate(date) {
  // Assuming you have an array of written posts called `writtenPosts`
  for (let i = 0; i < writtenPosts.length; i++) {
    const postDate = new Date(writtenPosts[i].createdAt); // Convert the post's created date to a Date object
    if (
      postDate.getDate() === date.getDate() &&
      postDate.getMonth() === date.getMonth() &&
      postDate.getFullYear() === date.getFullYear()
    ) {
      return true; // Found a post written on the given date
    }
  }
  return false; // No posts found for the given date
}