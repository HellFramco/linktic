import React, { SVGProps, ReactElement } from 'react';

type IconName = 'Icon_x_talentic_2';

const icons: Record<IconName, ReactElement<SVGProps<SVGSVGElement>>> = {
  Icon_x_talentic_2: (
    <svg
      id="Capa_1"
      data-name="Capa 1"
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 100 100"
    >
      <path d="M54.53,50L89.71,14.82c1.25-1.25,1.25-3.28,0-4.53-1.25-1.25-3.28-1.25-4.53,0l-35.18,35.18L14.82,10.29c-1.25-1.25-3.28-1.25-4.53,0-1.25,1.25-1.25,3.28,0,4.53l35.18,35.18L10.29,85.18c-1.25,1.25-1.25,3.28,0,4.53.62.62,1.44.94,2.26.94s1.64-.31,2.26-.94l35.18-35.18,35.18,35.18c.62.62,1.44.94,2.26.94s1.64-.31,2.26-.94c1.25-1.25,1.25-3.28,0-4.53l-35.18-35.18Z" />
    </svg>
  ),
};

type IconProps = SVGProps<SVGSVGElement> & {
  name: IconName;
};

const Icon: React.FC<IconProps> = ({ name, ...props }) => {
  const IconComponent = icons[name];
  return IconComponent ? React.cloneElement(IconComponent, props) : null;
};

export default Icon;
