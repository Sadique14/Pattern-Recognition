function [X]=Centralize(X)
%Translates the data so that the center of the bounding box drawn
%around the data points is  moved into the origin of the coordinate 
%system. Datapoints are the rows of X and they consists of x-
%and y-coordinates (Nx2 matrices)


min1=min(X(:,1));
min2=min(X(:,2));
max1=max(X(:,1));
max2=max(X(:,2));
BBC=[min1+max1, min2+max2]/2; %Center of the bounding box
N=size(X,1);
for i=1:1:N
  X(i,:)=X(i,:)-BBC;
end