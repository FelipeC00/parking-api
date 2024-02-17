package com.example.demoparkapi.resources.dto.mapper;

import com.example.demoparkapi.entities.User;
import com.example.demoparkapi.resources.dto.UserRequestDto;
import com.example.demoparkapi.resources.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMapper {
    /**
     * Converts a UserRequestDto object into a User entity.
     *
     * This method takes a UserRequestDto object, which contains the data sent in the request,
     * and uses ModelMapper to convert this DTO into a User entity.
     *
     * @param requestDto the UserRequestDto object to be converted
     * @return a User entity
     */
    public static User toUser(UserRequestDto requestDto){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(requestDto, User.class);
        return user;
    }
    /**
     * Converts a User entity into a UserResponseDto object.
     *
     * This method takes a User entity, which contains the data of a user in the system,
     * and uses ModelMapper to convert this entity into a UserResponseDto object. The UserResponseDto
     * object is then returned to the client as a response.
     *
     * A custom PropertyMap is used to handle the mapping of the 'role' field. The 'role' field in the User entity
     * is a Role enum, but in the UserResponseDto, it's a string. Therefore, we need to convert the Role enum to a string
     * and remove the "ROLE_" prefix before setting it in the UserResponseDto.
     *
     * @param user the User entity to be converted
     * @return a UserResponseDto object
     */

    public static UserResponseDto toDto(User user){
       String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);
        return userResponseDto;
    }
    public static List<UserResponseDto> toListDto(List<User> users){
        List<UserResponseDto> list = users.stream().map(x -> toDto(x)).collect(Collectors.toList());
        return list;
    }


}
